package com.phms.controller.user;

import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.*;
import com.phms.service.*;
import com.phms.utils.MyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 健康评估
 */
@Controller("HealthController")
@RequestMapping("/health")
public class HealthController {
    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PetService petService;
    @Autowired
    private PetDailyService petDailyService;
    @Autowired
    private UserService userService;
    @Autowired
    private StandardService standardService;

    /**
     * 分析页面
     */
    @RequestMapping("/assess")
    public String applyListDoctor(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Pet pet = new Pet();
        pet.setUserId(user.getId());
        pet.setPage(1);
        pet.setLimit(100);
        MMGridPageVoBean<Pet> voBean = (MMGridPageVoBean<Pet>) petService.getAllByLimit(pet);
        List<Pet> rows = voBean.getRows();
        // 获取到该用户下所有的宠物
        model.addAttribute("pets", rows);
        List<Long> applyCount = new ArrayList<>();
        List<String> dsCount = new ArrayList<>();
        List<String> tsCount = new ArrayList<>();
        List<String> wsCount = new ArrayList<>();
        List<String> hsCount = new ArrayList<>();
        List<String> asCount = new ArrayList<>();

        List<Double> pt = new ArrayList<>();
        List<Double> pw = new ArrayList<>();
        List<Double> ph = new ArrayList<>();
        List<Double> pa = new ArrayList<>();

        List<Double> mt = new ArrayList<>();
        List<Double> mw = new ArrayList<>();
        List<Double> mh = new ArrayList<>();
        List<Double> ma = new ArrayList<>();

        for(Pet p: rows){
            // 获取预约次数
            Appointment appointment = new Appointment();
            appointment.setPetId(p.getId());
            appointment.setPage(1);
            appointment.setLimit(1000);
            MMGridPageVoBean<Appointment>  as = (MMGridPageVoBean<Appointment>) appointmentService.getAllByLimit(appointment);
            applyCount.add(as==null? 0L : as.getTotal());

            // 获取就诊记录
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setPetId(p.getId());
            diagnosis.setPage(1);
            diagnosis.setLimit(10);
            MMGridPageVoBean<Diagnosis>  ds = (MMGridPageVoBean<Diagnosis>) diagnosisService.getAllByLimit(diagnosis);
            List<Diagnosis> dsRows = ds.getRows();
            int diagnosisStatus = 0;
            for (Diagnosis d: dsRows){
                diagnosisStatus += d.getStatus();
            }
            int sw = diagnosisStatus / dsRows.size();
            switch (sw){
                case 1:dsCount.add(p.getName() + "  宠物健康,请继续保持");break;
                case 2:dsCount.add(p.getName() + "  宠物异常请及时就诊!");break;
                case 3:dsCount.add(p.getName() + "  宠物病情比较严重,请及时就医!");break;
                case 4:dsCount.add(p.getName() + "  很抱歉宠物已无法治疗!");break;
                default:dsCount.add(p.getName() + "  宠物基本健康,请继续保持");break;
            }

            // 获取宠物日志
            PetDaily petDaily = new PetDaily();
            petDaily.setPetId(p.getId());
            petDaily.setPage(1);
            petDaily.setLimit(10);
            MMGridPageVoBean<PetDaily>  ps = (MMGridPageVoBean<PetDaily>) petDailyService.getAllByLimit(petDaily);
            List<PetDaily> psRows = ps.getRows();
            double t = 0;
            double w = 0;
            double h = 0;
            double a = 0;

            for (PetDaily petDaily1 : psRows){
                t+=petDaily1.getTemperature();
                w+=petDaily1.getWeight();
                h+=petDaily1.getHeight();
                a+=petDaily1.getAppetite();
            }
            t = t/psRows.size();
            w = w/psRows.size();
            h = h/psRows.size();
            a = a/psRows.size();

            pt.add(t);
            pw.add(w);
            ph.add(h);
            pa.add(a);

            // 获取标准
            Standard standard = new Standard();
            // 对应宠物类型
            standard.setType(p.getType());
            // 健康标准
            standard.setStatus(1);
            int petAge = MyUtils.get2DateDay(MyUtils.getDate2String(p.getBirthday(), "yyyy-MM-dd"), MyUtils.getDate2String(new Date(), "yyyy-MM-dd"));
            petAge = (petAge<0? -petAge : petAge)/365;
            // 年龄
            standard.setAgeMax(petAge);
            standard.setPage(1);
            standard.setLimit(100);
            MMGridPageVoBean<Standard>  ss = (MMGridPageVoBean<Standard>) standardService.getAllByLimit(standard);
            List<Standard> ssRows = ss.getRows();
            double tsMin = 0;
            double tsMax = 0;
            double wsMin = 0;
            double wsMax = 0;
            double hsMin = 0;
            double hsMax = 0;
            double asMin = 0;
            double asMax = 0;
            for (Standard s : ssRows){
                tsMin+=s.getTempMin();
                tsMax+=s.getTempMax();
                wsMin+=s.getWeightMin();
                wsMax+=s.getWeightMax();
                hsMin+=s.getHeightMin();
                hsMax+=s.getHeightMax();
                asMin+=s.getAppetiteMin();
                asMax+=s.getAppetiteMax();
            }
            tsMin = tsMin / ssRows.size();
            tsMax = tsMax / ssRows.size();
            wsMin = wsMin / ssRows.size();
            wsMax = wsMax / ssRows.size();
            hsMin = hsMin / ssRows.size();
            hsMax = hsMax / ssRows.size();
            asMin = asMin / ssRows.size();
            asMax = asMax / ssRows.size();

            mt.add(tsMax);
            mw.add(wsMax);
            mh.add(hsMax);
            ma.add(asMax);

            if (t>=tsMin && t<=tsMax){
                tsCount.add("  体温正常");
            }else if (t<tsMin){
                tsCount.add( "  体温偏低");
            }else if (t>tsMax){
                tsCount.add( "  体温偏高");
            }

            if (w>=wsMin && w<=wsMax){
                wsCount.add( "  体重正常");
            }else if (w<wsMin){
                wsCount.add("  体重偏低");
            }else if (w>wsMax){
                wsCount.add("  体重偏高");
            }

            if (h>=hsMin && h<=hsMax){
                hsCount.add("  身高正常");
            }else if (h<hsMin){
                hsCount.add( "  身高偏低");
            }else if (h>hsMax){
                hsCount.add("  身高偏高");
            }

            if (a>=asMin && a<=asMax){
                asCount.add( "  饭量正常");
            }else if (a<asMin){
                asCount.add("  饭量偏低");
            }else if (a>asMax){
                asCount.add("  饭量偏高");
            }
        }
        model.addAttribute("pets", rows);
        model.addAttribute("tsCount", tsCount);
        model.addAttribute("wsCount", wsCount);
        model.addAttribute("hsCount", hsCount);
        model.addAttribute("asCount", asCount);
        model.addAttribute("dsCount", dsCount);
        System.out.println(pt);
        model.addAttribute("pt", pt);
        model.addAttribute("ph", ph);
        model.addAttribute("pw", pw);
        model.addAttribute("pa", pa);

        model.addAttribute("mt", mt);
        model.addAttribute("mh", mh);
        model.addAttribute("mw", mw);
        model.addAttribute("ma", ma);
        return "tj/assess";
    }

    /**
     * 普通用户预约统计
     */
    @RequestMapping("/tjApply")
    public String tjApply(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Appointment appointment = new Appointment();
        appointment.setUserId(user.getId());
        appointment.setPage(1);
        appointment.setLimit(99999);
        MMGridPageVoBean<Appointment> voBean = (MMGridPageVoBean<Appointment>)  appointmentService.getAllByLimit(appointment);
        List<Appointment> rows = voBean.getRows();

        Integer a1 = 0;
        Integer a2 = 0;
        Integer a3 = 0;
        Integer a4 = 0;
        for (Appointment a: rows){
            switch (a.getStatus()){
                case 1: a1++;break;
                case 2: a2++;break;
                case 3: a3++;break;
                case 4: a4++;break;
            }
        }
        model.addAttribute("a1", a1);
        model.addAttribute("a2", a2);
        model.addAttribute("a3", a3);
        model.addAttribute("a4", a4);

        return "tj/tjApply";
    }

    /**
     * 医生预约统计
     */
    @RequestMapping("/tjApplyDoctor")
    public String tjApplyDoctor(Model model) {
        Appointment appointment = new Appointment();
        appointment.setPage(1);
        appointment.setLimit(99999);
        MMGridPageVoBean<Appointment> voBean = (MMGridPageVoBean<Appointment>)  appointmentService.getAllByLimit(appointment);
        List<Appointment> rows = voBean.getRows();

        Integer a1 = 0;
        Integer a2 = 0;
        Integer a3 = 0;
        Integer a4 = 0;
        for (Appointment a: rows){
            switch (a.getStatus()){
                case 1: a1++;break;
                case 2: a2++;break;
                case 3: a3++;break;
                case 4: a4++;break;
            }
        }
        model.addAttribute("a1", a1);
        model.addAttribute("a2", a2);
        model.addAttribute("a3", a3);
        model.addAttribute("a4", a4);

        return "tj/tjApplyDoctor";
    }

    /**
     * 普通用户宠物日志统计
     */
    @RequestMapping("/tjDaily")
    public String tjDaily(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Pet pet = new Pet();
        pet.setUserId(user.getId());
        pet.setPage(1);
        pet.setLimit(99999);
        MMGridPageVoBean<Pet> voBean = (MMGridPageVoBean<Pet>)  petService.getAllByLimit(pet);
        List<Pet> rows = voBean.getRows();

        model.addAttribute("pets", rows);
        if (rows.size()>0){
            pet = rows.get(0);
            PetDaily daily = new PetDaily();
            daily.setPetId(pet.getId());
            daily.setPage(1);
            daily.setLimit(99999);
            MMGridPageVoBean<PetDaily> ppp = (MMGridPageVoBean<PetDaily>)  petDailyService.getAllByLimit(daily);
            List<PetDaily> list = ppp.getRows();

            for (PetDaily p : list){
                p.setDateTime(MyUtils.getDate2String(p.getCreateTime(), "yyyy-MM-dd"));
            }

            model.addAttribute("dailys", list);
        }

        return "tj/tjDaily";
    }
    /**
     * 医生宠物日志统计
     */
    @RequestMapping("/tjDailyDoctor")
    public String tjDailyDoctor(Model model) {
        Pet pet = new Pet();
        pet.setPage(1);
        pet.setLimit(99999);
        MMGridPageVoBean<Pet> voBean = (MMGridPageVoBean<Pet>)  petService.getAllByLimit(pet);
        List<Pet> rows = voBean.getRows();

        model.addAttribute("pets", rows);
        if (rows.size()>0){
            pet = rows.get(0);
            PetDaily daily = new PetDaily();
            daily.setPetId(pet.getId());
            daily.setPage(1);
            daily.setLimit(99999);
            MMGridPageVoBean<PetDaily> ppp = (MMGridPageVoBean<PetDaily>)  petDailyService.getAllByLimit(daily);
            List<PetDaily> list = ppp.getRows();

            for (PetDaily p : list){
                p.setDateTime(MyUtils.getDate2String(p.getCreateTime(), "yyyy-MM-dd"));
            }

            model.addAttribute("dailys", list);
        }

        return "tj/tjDailyDoctor";
    }

    /**
     * 普通用户查询条件数据返回宠物日志
     */
    @RequestMapping("/tjDailyData")
    @ResponseBody
    public Object tjDailyData(Long id){
        PetDaily daily = new PetDaily();
        daily.setPetId(id);
        daily.setPage(1);
        daily.setLimit(99999);
        MMGridPageVoBean<PetDaily> ppp = (MMGridPageVoBean<PetDaily>)  petDailyService.getAllByLimit(daily);
        List<PetDaily> list = ppp.getRows();
        for (PetDaily p : list){
            p.setDateTime(MyUtils.getDate2String(p.getCreateTime(), "yyyy-MM-dd"));
        }
        return list;
    }

    /**
     * 医生查询条件数据返回宠物日志
     */
    @RequestMapping("/tjDailyDataDoctor")
    @ResponseBody
    public Object tjDailyDataDoctor(Long id){
        PetDaily daily = new PetDaily();
        daily.setPetId(id);
        daily.setPage(1);
        daily.setLimit(99999);
        MMGridPageVoBean<PetDaily> ppp = (MMGridPageVoBean<PetDaily>)  petDailyService.getAllByLimit(daily);
        List<PetDaily> list = ppp.getRows();
        for (PetDaily p : list){
            p.setDateTime(MyUtils.getDate2String(p.getCreateTime(), "yyyy-MM-dd"));
        }
        return list;
    }


    /**
     * 用户查看医生空闲时间
     */
    @RequestMapping(value = "/freeTime")
    public String freeTime(Model model) {
        List<User> doctors = userService.listDoctor();
        model.addAttribute("doctors", doctors);

        Long docId = doctors.get(0).getId();
        model.addAttribute("docName", doctors.get(0).getName());
        String nowDateYMD = MyUtils.getNowDateYMD();

        List<Map<String, Object>> map = appointmentService.getFreeTimeById(docId, nowDateYMD+MyUtils.START_HOUR);
        List<String> time = new ArrayList<>();
        List<Long> value = new ArrayList<>();

        for (Map<String, Object> m : map){
            String df = (String) m.get("df");
            time.add(df);
            Long v = (Long) m.get("c");
            if (v == null) {
                value.add(0L);
            }else {
                value.add(v);
            }
        }

        model.addAttribute("time", time);
        model.addAttribute("value", value);

        return "tj/freeTime";
    }

    @RequestMapping(value = "/getFreeTime")
    @ResponseBody
    public Object getFreeTime(Long id, String date) {
        User doctors = userService.selectByPrimaryKey(id);
        Map<String, Object> result = new HashMap<>();
        result.put("n", doctors.getName());
        List<Map<String, Object>> map = appointmentService.getFreeTimeById(id, date+MyUtils.START_HOUR);
        List<String> time = new ArrayList<>();
        List<Long> value = new ArrayList<>();

        for (Map<String, Object> m : map){
            String df = (String) m.get("df");
            time.add(df+"点");
            Long v = (Long) m.get("c");
            if (v == null) {
                value.add(0L);
            }else {
                value.add(v);
            }
        }
        result.put("t", time);
        result.put("v", value);
        return result;
    }
}

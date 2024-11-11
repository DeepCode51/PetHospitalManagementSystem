$(function(){
        $(".error-pass, .error-userName").hide();
        $(".overlay").hide();
        $(".confirmation").hide();
      })
      
      function validateForm() {
        var countErrors = 0;
        var userName = $("#userName");
        var passInput = $("input[type=password]");
        
        if(userName.val() < 1) {
          $(".error-userName").fadeIn();
          $(".userName-msg").html("用户名不能为空");
          $(userName).addClass("warning");
          countErrors++;
        } else {
          $(userName).removeClass("warning");
        }
      
        if(passInput.val().length < 5) {
          $(".error-pass").fadeIn();
          $(".pass-msg").html("密码长度不能小于5个字符");
          $(passInput).addClass("warning");
          countErrors++;
        } else {
          $(passInput).removeClass("warning");
        }
      
        setTimeout(function showErrorMsg() {
          $(".error-userName, .error-pass").fadeOut();
        }, 2000)
      
        if(countErrors === 0) {
          $(".overlay").show();
          $(".confirmation").show();
        }
      }
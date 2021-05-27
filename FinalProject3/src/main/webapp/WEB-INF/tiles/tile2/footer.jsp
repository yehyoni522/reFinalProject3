<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

	<div id="footerSchoolName">
			<span style="font-size: 25pt; font-weight: bold;">쌍용대학교Ssangyong</span><br>
			<span style="color: #999999;">UniversityCopyright 2021. Ssangyong University. All Rights Reserved.</span> 
	</div>	
	
	<div id="address">
		서울특별시 마포구 월드컵북로 21 2층 풍성빌딩<br>
			TEL 02-336-8546 /<br>
			FAX 02-336-8546
	</div>
	

<script type="text/javascript">

	$(document).ready(function(){
		func_height();
	});

    function func_height() {
      var content_height = $("div#mycontent").height(); 
      var sideinfo_height = $("div#mysideinfo").height();
      
      if(content_height > sideinfo_height) {
    	  console.log("content_height : " + content_height+", sideinfo_height : " + sideinfo_height);
    	  $("div#mysideinfo").height(content_height);
      }
      
      if(content_height < sideinfo_height) {
    	  console.log("content_height : " + content_height+", sideinfo_height : " + sideinfo_height);
    	  $("div#mycontent").height(sideinfo_height);
      }
      
      content_height = $("div#mycontent").height(); 
      sideinfo_height = $("div#mysideinfo").height();
      console.log("content_height : " + content_height+", sideinfo_height : " + sideinfo_height);
    }
</script>   

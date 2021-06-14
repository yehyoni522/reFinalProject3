package com.spring.finalproject3.hyeminJang.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.common.MyUtil;
import com.spring.finalproject3.common.Sha256;
import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.model.OutboxVO;

import com.spring.finalproject3.hyeminJang.service.InterMessageService;
import com.spring.finalproject3.joseungjin.model.PersonVO;

@Controller
public class HyeminJang_Controller {
   
   @Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
   private InterMessageService service;
   
   // 마이페이지 보기
   @RequestMapping(value="/mypage/mypage.sam")
   public ModelAndView requiredLogin_mypage(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	   
	   HttpSession session = request.getSession(); 
	   PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
	   
	   // 안읽은 글의 갯수 세기
	   int nonReadCount = service.getNonReadCount(loginuser.getPerno());// 로그인한 사람의 id값이 들어가야함
	   mav.addObject("nonReadCount", nonReadCount);
	   
	   mav.addObject("name", loginuser.getName()); // 이름 불러오기
	   mav.addObject("email", loginuser.getEmail()); // 이메일
	   mav.addObject("identity", loginuser.getIdentity()); //교수 or 학생
	   mav.addObject("perno", loginuser.getPerno()); // 학번/교번
	  
	   int rcheck = service.getRcheck(loginuser.getPerno()); //예약유무 가져오기
	   
	   mav.addObject("rcheck", rcheck); //예약유무 가져오기
	   
		/* System.out.println("rcheck"+rcheck ); */
	   
	   if(rcheck != 0) {
		  // List<Map<String, String>> booklist = service.getBooking(loginuser.getPerno()); // 예약한 내용가져오기(날짜별다)
		   
		  // mav.addObject("booklist", booklist);
		   
		   Map<String, String> bookToday = service.getBookingToday(loginuser.getPerno());  // 오늘것 가져오기
		   mav.addObject("bookToday", bookToday);
	   }
	   
	   int majseq =  loginuser.getFk_majseq();
       String nameMaj = service.getNameMaj(majseq); //학과이름 알아오기
       mav.addObject("nameMaj", nameMaj); // 학과이름
       
       // 학생이 듣고있는 수업정보가지고오기
      List< Map<String, String> > scorevolist = service.getscoreList(loginuser.getPerno());
      mav.addObject("scorevolist",scorevolist);
       
       
	   
	   mav.setViewName("mypage/mypage.tiles2");
	    
	   return mav;
   }

   // 입실확인update하기
   @RequestMapping(value="/mypage/updateUsecheck.sam")
   public ModelAndView updateUsecheck(HttpServletRequest request, ModelAndView mav) {
	   
	   String bno = request.getParameter("bno");
	   
	   int n = service.updateUsecheck(bno);
	   
	   mav.setViewName("redirect:/mypage/mypage.sam");
	    
	   return mav;
   }
   
   // 회원정보수정하기
   @RequestMapping(value="/mypage/edit.sam")
   public ModelAndView requiredLogin_editMyInfo(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
	   
	   HttpSession session = request.getSession(); 
	   PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
	  
	   
	   int majseq =  loginuser.getFk_majseq();
       String nameMaj = service.getNameMaj(majseq); //학과이름 알아오기
       String nameCol = service.getNameCol(majseq); //단대이름 알아오기
       mav.addObject("nameMaj", nameMaj); // 학과이름
       mav.addObject("nameCol", nameCol); // 단대이름
       
	   mav.setViewName("mypage/editMyInfo.tiles2");
	    
	   return mav;
   }
   
   // 회원정보수정하기
   @RequestMapping(value="/mypage/editEnd.sam")
   public ModelAndView requiredLogin_editEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			
		String perno = request.getParameter("perno");
		String pwd = request.getParameter("pwd"); 
		String email = request.getParameter("email"); 
		String hp1 = request.getParameter("hp1"); 
		String hp2 = request.getParameter("hp2"); 
		String hp3 = request.getParameter("hp3"); 
		String address = request.getParameter("address"); 
		
		
		String mobile = hp1 + "-"+ hp2 +"-"+ hp3;
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("perno", perno);
		paraMap.put("pwd", Sha256.encrypt(pwd));
		paraMap.put("email", email);
		paraMap.put("address", address);
		paraMap.put("mobile", mobile);
		
		int n  = service.updateInfo(paraMap);
		
		if(n==0) {
			String message = "회원정보 수정을 실패하셨습니다.!!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			mav.setViewName("msg");
		}
		else {
			String message = "회원정보수정을 성공하였습니다.!!";
			String loc = "redirect:/mypage/mypage.sam";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			mav.setViewName("msg");
		}
			
		
	
	    
	   return mav;
   }
   
   
   // 받은 쪽지함 (리스트) 보기 
   @RequestMapping(value="/message/inbox.sam")
   public ModelAndView requiredLogin_inbox(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
      List<InboxVO> inboxList = null;
		
	  HttpSession session = request.getSession(); 
	  PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
	
	 
		// 안읽은 글의 갯수 세기
	      int nonReadCount = service.getNonReadCount(loginuser.getPerno());// 로그인한 사람의 id값이 들어가야함
	      
	      mav.addObject("nonReadCount", nonReadCount);

	      // 안읽은 글만 보이기
	      String readState = request.getParameter("readState"); 
	      if(readState == null) {
	         readState = "";
	      }
	      
	      // == 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 == //
	      String searchType = request.getParameter("searchType"); 
	      String searchWord = request.getParameter("searchWord");
	      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
	      
	      if(searchType == null || (!"subject".equals(searchType) && !"inboxname".equals(searchType)) ) {
	      searchType = "";
	      }
	      
	      if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {
	      searchWord = "";
	      }
	      
	      Map<String, String> paraMap = new HashMap<String, String>();
	      paraMap.put("searchType", searchType);
	      paraMap.put("searchWord", searchWord);
	      paraMap.put("userid", String.valueOf(loginuser.getPerno()));
	      
	      // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
	      // 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.
	      int totalCount = 0;         // 총 게시물 건수
	      //   int sizePerPage = 10;       // 한 페이지당 보여줄 게시물 건수
	      int sizePerPage = 10;        // 한 페이지당 보여줄 게시물 건수
	      int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
	      int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)  
	      
	      int startRno = 0;           // 시작 행번호
	      int endRno = 0;             // 끝 행번호 

	      // 총 게시물 건수(totalCount)
	      totalCount = service.getTotalCount(paraMap);
	      
	      
	      paraMap.put("readState", readState);
	      
	      mav.addObject("totalCount", totalCount);
	      
	      totalPage = (int) Math.ceil( (double)totalCount/sizePerPage ); 
	      
	      if(str_currentShowPageNo == null) {
	         // 게시판에 보여지는 초기화면 
	         currentShowPageNo = 1;
	      }
	      else {
	         try {
	            currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
	            
	            if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
	               currentShowPageNo = 1;
	            }
	   
	         } catch (NumberFormatException e) {
	            currentShowPageNo = 1;
	         }
	      }

	      startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
	      endRno = startRno + sizePerPage - 1;

	      paraMap.put("startRno", String.valueOf(startRno));
	      paraMap.put("endRno", String.valueOf(endRno));

	      inboxList = service.inboxListSearchWithPaging(paraMap);
	      // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)

	      // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
	      if(!"".equals(searchType) && !"".equals(searchWord)) {
	         mav.addObject("paraMap", paraMap);
	      }


	      // === #121. 페이지바 만들기 === //
	      //   int blockSize = 10;
	      int blockSize = 10;
	      int loop = 1;
	      

	      int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
	      
	      String pageBar = "<ul style='list-style: none;'>";
	      String url = "inbox.sam";

	      // === [맨처음][이전] 만들기 === 
	      if(pageNo != 1) {
	         pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
	         pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
	      }

	      while( !(loop > blockSize || pageNo > totalPage) ) {

	         if(pageNo == currentShowPageNo) {
	            pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; color:black; padding:2px 4px;'>"+pageNo+"</li>";
	         }
	         else {
	            pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
	         }

	         loop++;
	         pageNo++;
	         
	      }// end of while------------------------


	      // === [다음][마지막] 만들기 === 
	      if(pageNo <= totalPage) {
	         pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
	         pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
	      }

	      pageBar += "</ul>";

	      mav.addObject("pageBar",pageBar);
	      
	      String gobackURL = MyUtil.getCurrentURL(request);
	      mav.addObject("gobackURL", gobackURL);
	      
	      mav.addObject("inboxList", inboxList);   
	      mav.setViewName("message/inbox.tiles2");
	    
      return mav;
   }
   
   // 받은 쪽지 보기
   @RequestMapping(value="/message/inView.sam")
   public ModelAndView requiredLogin_inView(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
	  HttpSession session = request.getSession(); 
	  PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
	
		// 조회하고자 하는 글번호 받아오기
	      String str_inboxSeq = request.getParameter("inboxSeq");
	      try {
	         int inboxSeq = Integer.parseInt(str_inboxSeq);
	         
	         InboxVO inboxvo = null;
	         
	         inboxvo = service.getInView(inboxSeq);
	         
	         mav.addObject("inboxvo", inboxvo);
	         
	         // 안읽은 글의 갯수 세기
	         int nonReadCount = service.getNonReadCount(loginuser.getPerno());// 로그인한 사람의 id값이 들어가야함
	         
	         mav.addObject("nonReadCount", nonReadCount);
	      
	      } catch(NumberFormatException e) {
	         
	      }
	      
	      mav.setViewName("message/inView.tiles2");
	 
      
      
      return mav;
   }
   
   // 쪽지 쓰기
   @RequestMapping(value="/message/write.sam")
   public ModelAndView requiredLogin_write(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
      
	  HttpSession session = request.getSession(); 
	  PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
	
	  
		// 안읽은 글의 갯수 세기
	      int nonReadCount = service.getNonReadCount(loginuser.getPerno());// 로그인한 사람의 id값이 들어가야함
	      
	      mav.addObject("nonReadCount", nonReadCount);
	      // 기본적으로 들어가야 하는 것
	      
	      
	      
	      mav.addObject("loginuser_id",loginuser.getPerno());
	      mav.addObject("loginuser_name",loginuser.getName());
	      
	      String fk_userid = request.getParameter("fk_userid");
	      String name = request.getParameter("name");
	      
	      if(fk_userid != null && name!= null) {
	    	  mav.addObject("fk_userid",fk_userid);
		      mav.addObject("fk_name",name);
	      }
	      
	      
	      mav.setViewName("message/write.tiles2");
	 
      
      return mav;
   }
   
   // 쪽지 insert하기
   @RequestMapping(value="/message/writeEnd.sam", method= {RequestMethod.POST})
   public ModelAndView writeEnd(HttpServletRequest request,ModelAndView mav) {
      
	  HttpSession session = request.getSession(); 
	  PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
	
	  if(loginuser == null) {
		  
	         String message = "로그인먼저 진행해주세요.";
	         String loc = "javascript:history.back()";
	         
	         mav.addObject("message", message);
	         mav.addObject("loc", loc);
	         
	         mav.setViewName("msg");
	  }
	  else {
		  Map<String,String> paraMap = new HashMap<>();
	      int n =0;
	      
	      String name_es = request.getParameter("name_es"); // 이름들
	      String receiver_es = request.getParameter("receiver_es"); // 번호들
	      String text = request.getParameter("DOC_TEXT");
	      String sender = loginuser.getName();
	      
	      
	      String receiverArr[] = receiver_es.split(",");
	      String nameArr[] = name_es.split(",");
	      
	      for(int i =0; i<receiverArr.length; i++) {
	         String receiver = receiverArr[i];
	         String name = nameArr[i];
	         
	         paraMap.put("receiver", receiver);
	         paraMap.put("name", name);// 받는 사람
	         paraMap.put("sender", sender);// 보내는 사람
	         paraMap.put("text", text);
	         paraMap.put("login_userid", String.valueOf(loginuser.getPerno()));// 현재 로그인된 아이디를 1004라고 가정한다.
	         
	         // 쪽지작성 insert
	          n = service.writeEnd(paraMap); 
	         // n 이 1 이라면 정상적으로 insert
	         // n 이 0 이라면 insert안됌
	         
	      }
	      if(n == 1) {
	         // 성공
	          String message = "쪽지 전송에 성공하였습니다.";
	          String loc = request.getContextPath()+"/message/inbox.sam";
	          
	         mav.addObject("message", message);
	         mav.addObject("loc", loc);
	          
	         mav.setViewName("msg");
	         
	      }
	      else {
	         // 실패
	         String message = "쪽지 전송에 실패하였습니다.";
	         String loc = "javascript:history.back()";
	         
	         mav.addObject("message", message);
	         mav.addObject("loc", loc);
	         
	         mav.setViewName("msg");
	      }
	      
	      
	      
	  }
      
      return mav;
   }

     // 아이디 찾아보기
     @RequestMapping(value="/message/userFind.sam") 
     public String userFind(HttpServletRequest request) {
        String receiver = request.getParameter("receiver");
        
        request.setAttribute("receiver", receiver);
        return "tiles2/message/userFind";
        
    }
     
     // inbox에서 체크박스에서 선택된 쪽지  삭제하기 
     @ResponseBody
     @RequestMapping(value="/message/goInDel.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
     public String inDel(@RequestParam(value="seqArr[]") List<Integer> deleteList, HttpServletRequest request) {
        
        ArrayList<Integer> deleteArray = new ArrayList<Integer>();
          for(int i=0;i<deleteList.size();i++){
              deleteArray.add(deleteList.get(i));
          }
        
          
          int n = service.inDel(deleteArray); 
          
          
      
         JSONObject jsonObj = new JSONObject(); // {}
         jsonObj.put("n", n);
      
         return jsonObj.toString(); 
   
   }
     
     // 세부읽기에서 한개만 쪽지 삭제하기
     @RequestMapping(value="/message/inDelOne.sam")
      public ModelAndView inDelOne(HttpServletRequest request,ModelAndView mav) {
         
           String inboxSeq = request.getParameter("inboxSeq");

         int n = service.inDelOne(Integer.parseInt(inboxSeq));
         
         if (n==1) {
            mav.setViewName("redirect:/message/inbox.sam");
         }
         
         
         return mav;
      }
     
     // 사람번호검색
     @ResponseBody
     @RequestMapping(value="/message/searchPerson.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
     public String searchPerson(HttpServletRequest request) {
        
        String perno = request.getParameter("perno");
        
        PersonVO pervo = service.searchPerson(Integer.parseInt(perno));
        
        JSONObject jsonObj = new JSONObject(); // {}
        
        if(pervo != null) {
          
           
          int majseq =  pervo.getFk_majseq();
          String nameMaj = service.getNameMaj(majseq); //학과이름 알아오기
          
            jsonObj.put("name", pervo.getName());
            jsonObj.put("perno", pervo.getPerno());
            if(pervo.getIdentity() == 0) {
               jsonObj.put("identity", "학생");
            }
            else if(pervo.getIdentity() == 1) {
               jsonObj.put("identity", "교수");
            }
            else {
               jsonObj.put("identity", "관리자");
            }
            
            jsonObj.put("nameMaj", nameMaj);
        }
        
      
         return jsonObj.toString(); 
   
   }
     
 
     
     // inbox에서 체크박스에서 선택된 쪽지  삭제하기 
     @ResponseBody
     @RequestMapping(value="/message/goOutDel.sam", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")
     public String outDel(@RequestParam(value="seqArr[]") List<Integer> deleteList, HttpServletRequest request) {
        
        ArrayList<Integer> deleteArray = new ArrayList<Integer>();
          for(int i=0;i<deleteList.size();i++){
              deleteArray.add(deleteList.get(i));
          }
        
          
          int n = service.outDel(deleteArray); 
          
          
      
         JSONObject jsonObj = new JSONObject(); // {}
         jsonObj.put("n", n);
      
         return jsonObj.toString(); 
   
   }
     
     // 보낸 쪽지함 (리스트) 보기 
     @RequestMapping(value="/message/outbox.sam")
     public ModelAndView requiredLogin_outbox(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
        
        List<OutboxVO> outboxList = null;
  		
  	  HttpSession session = request.getSession(); 
  	  PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
  	
  	 
  		// 안읽은 글의 갯수 세기
         int nonReadCount = service.getNonReadCount(loginuser.getPerno());// 로그인한 사람의 id값이 들어가야함
         
         mav.addObject("nonReadCount", nonReadCount);
  	      // == 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 == //
  	      String searchType = request.getParameter("searchType"); 
  	      String searchWord = request.getParameter("searchWord");
  	      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
  	      
  	      if(searchType == null || (!"subject".equals(searchType) && !"outboxName".equals(searchType)) ) {
  	      searchType = "";
  	      }
  	      
  	      if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {
  	      searchWord = "";
  	      }
  	      
  	      Map<String, String> paraMap = new HashMap<String, String>();
  	      paraMap.put("searchType", searchType);
  	      paraMap.put("searchWord", searchWord);
  	      paraMap.put("userid", String.valueOf(loginuser.getPerno()));
  	      
  	      // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
  	      // 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.
  	      int totalCount = 0;         // 총 게시물 건수
  	      //   int sizePerPage = 10;       // 한 페이지당 보여줄 게시물 건수
  	      int sizePerPage = 10;        // 한 페이지당 보여줄 게시물 건수
  	      int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
  	      int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)  
  	      
  	      int startRno = 0;           // 시작 행번호
  	      int endRno = 0;             // 끝 행번호 

  	      // 총 게시물 건수(totalCount)
  	      totalCount = service.getTotalCountout(paraMap);
  	      
  	      mav.addObject("totalCount", totalCount);
  	      
  	      totalPage = (int) Math.ceil( (double)totalCount/sizePerPage ); 
  	      
  	      if(str_currentShowPageNo == null) {
  	         // 게시판에 보여지는 초기화면 
  	         currentShowPageNo = 1;
  	      }
  	      else {
  	         try {
  	            currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
  	            
  	            if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
  	               currentShowPageNo = 1;
  	            }
  	   
  	         } catch (NumberFormatException e) {
  	            currentShowPageNo = 1;
  	         }
  	      }

  	      startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
  	      endRno = startRno + sizePerPage - 1;

  	      paraMap.put("startRno", String.valueOf(startRno));
  	      paraMap.put("endRno", String.valueOf(endRno));

  	      outboxList = service.outboxListSearchWithPaging(paraMap);
  	      // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것) <<outbox>>

  	      // 아래는 검색대상 컬럼과 검색어를 유지시키기 위한 것임.
  	      if(!"".equals(searchType) && !"".equals(searchWord)) {
  	         mav.addObject("paraMap", paraMap);
  	      }


  	      // === #121. 페이지바 만들기 === //
  	      //   int blockSize = 10;
  	      int blockSize = 10;
  	      int loop = 1;
  	      

  	      int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
  	      
  	      String pageBar = "<ul style='list-style: none;'>";
  	      String url = "outbox.sam";

  	      // === [맨처음][이전] 만들기 === 
  	      if(pageNo != 1) {
  	         pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
  	         pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
  	      }

  	      while( !(loop > blockSize || pageNo > totalPage) ) {

  	         if(pageNo == currentShowPageNo) {
  	            pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;  color:black; padding:2px 4px;'>"+pageNo+"</li>";
  	         }
  	         else {
  	            pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
  	         }

  	         loop++;
  	         pageNo++;
  	         
  	      }// end of while------------------------


  	      // === [다음][마지막] 만들기 === 
  	      if(pageNo <= totalPage) {
  	         pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
  	         pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
  	      }

  	      pageBar += "</ul>";

  	      mav.addObject("pageBar",pageBar);
  	      
  	      String gobackURL = MyUtil.getCurrentURL(request);
  	      mav.addObject("gobackURL", gobackURL);
  	      
  	      mav.addObject("outboxList", outboxList);   
  	      mav.setViewName("message/outbox.tiles2");
  	      // /WEB-INF/views/tiles2/message/inbox.jsp 파일을 생성한다.
  	 
        return mav;
     }
     
     // 보낸 쪽지 보기
     @RequestMapping(value="/message/outView.sam")
     public ModelAndView requiredLogin_outView(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
        
  	  HttpSession session = request.getSession(); 
  	  PersonVO loginuser = (PersonVO)session.getAttribute("loginuser");
  	
  	 
  	      String str_outboxSeq = request.getParameter("outboxSeq");
  	      try {
  	         int outboxSeq = Integer.parseInt(str_outboxSeq);
  	         
  	         OutboxVO outboxvo = null;
  	         
  	         outboxvo = service.getOutView(outboxSeq);
  	         
  	         mav.addObject("outboxvo", outboxvo);
  	         
  	         // 안읽은 글의 갯수 세기
  	         int nonReadCount = service.getNonReadCount(loginuser.getPerno());// 로그인한 사람의 id값이 들어가야함
  	         
  	         mav.addObject("nonReadCount", nonReadCount);
  	      
  	      } catch(NumberFormatException e) {
  	         
  	      }
  	      
  	      mav.setViewName("message/outView.tiles2");
  	  
        
        return mav;
     }
     
     // 세부읽기에서 한개만 쪽지 삭제하기<<outbox>>
     @RequestMapping(value="/message/outDelOne.sam")
      public ModelAndView outDelOne(HttpServletRequest request,ModelAndView mav) {
         
           String outboxSeq = request.getParameter("outboxSeq");

         int n = service.outDelOne(Integer.parseInt(outboxSeq));
         
         if (n==1) {
            mav.setViewName("redirect:/message/outbox.sam");
         }
         
         
         return mav;
      }
     
 	// ==== #173. 웹채팅관련  4 ====
	@RequestMapping(value="/chatting/multichat.sam", method= {RequestMethod.GET})
	public ModelAndView multichat(ModelAndView mav) {
		   
		mav.setViewName("chatting/multichat");
		return mav;
	   }
	
	
   
}
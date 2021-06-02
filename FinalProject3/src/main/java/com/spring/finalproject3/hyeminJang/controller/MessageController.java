package com.spring.finalproject3.hyeminJang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finalproject3.hyeminJang.model.InboxVO;
import com.spring.finalproject3.hyeminJang.service.InterMessageService;
import com.spring.finalproject3.joseungjin.model.PersonVO;

@Controller
public class MessageController {
   
   @Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
   private InterMessageService service;
   
   // 받은 쪽지함 (리스트) 보기 
   @RequestMapping(value="/message/inbox.sam")
   public ModelAndView inbox(ModelAndView mav, HttpServletRequest request) {
      
      List<InboxVO> inboxList = null;
      
      
      
      /*
      HttpSession session = request.getSession();
      personVO loginuser = (MemberVO) session.getAttribute("loginuser");*/
      
      // 안읽은 글의 갯수 세기
      int userid = 20201234;
      int nonReadCount = service.getNonReadCount(userid);// 로그인한 사람의 id값이 들어가야함
      
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
      
      if(searchType == null || (!"subject".equals(searchType) && !"name".equals(searchType)) ) {
      searchType = "";
      }
      
      if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty()) {
      searchWord = "";
      }
      
      Map<String, String> paraMap = new HashMap<String, String>();
      paraMap.put("searchType", searchType);
      paraMap.put("searchWord", searchWord);
      paraMap.put("userid", String.valueOf(1004));
      paraMap.put("readState", readState);
      
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
      String url = "list.action";

      // === [맨처음][이전] 만들기 === 
      if(pageNo != 1) {
         pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
         pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
      }

      while( !(loop > blockSize || pageNo > totalPage) ) {

         if(pageNo == currentShowPageNo) {
            pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";
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
      
      mav.addObject("inboxList", inboxList);   
      mav.setViewName("message/inbox.tiles2");
      // /WEB-INF/views/tiles2/message/inbox.jsp 파일을 생성한다.
      
      return mav;
   }
   
   // 받은 쪽지 보기
   @RequestMapping(value="/message/inView.sam")
   public ModelAndView inView(HttpServletRequest request, ModelAndView mav) {
      
      /*
      HttpSession session = request.getSession();
      personVO loginuser = (MemberVO) session.getAttribute("loginuser");*/
      
      
      
      // 조회하고자 하는 글번호 받아오기
      String str_inboxSeq = request.getParameter("inboxSeq");
      
      try {
         int inboxSeq = Integer.parseInt(str_inboxSeq);
         
         InboxVO inboxvo = null;
         
         inboxvo = service.getInView(inboxSeq);
         
         mav.addObject("inboxvo", inboxvo);
         
         // 안읽은 글의 갯수 세기
         int userid = 20201234;
         int nonReadCount = service.getNonReadCount(userid);// 로그인한 사람의 id값이 들어가야함
         
         mav.addObject("nonReadCount", nonReadCount);
      
      } catch(NumberFormatException e) {
         
      }
      
      mav.setViewName("message/inView.tiles2");
      
      
      return mav;
   }
   
   // 쪽지 쓰기
   @RequestMapping(value="/message/write.sam")
   public ModelAndView write(HttpServletRequest request,ModelAndView mav) {
      
      /*
      HttpSession session = request.getSession();
      personVO loginuser = (MemberVO) session.getAttribute("loginuser");*/
      
      // 안읽은 글의 갯수 세기
      int userid = 20201234;
      int nonReadCount = service.getNonReadCount(userid);// 로그인한 사람의 id값이 들어가야함
      
      mav.addObject("nonReadCount", nonReadCount);
      
      mav.setViewName("message/write.tiles2");
      // /WEB-INF/views/tiles2/message/write.jsp 파일을 생성한다.
      
      return mav;
   }
   
   // 쪽지 insert하기
   @RequestMapping(value="/message/writeEnd.sam", method= {RequestMethod.POST})
   public ModelAndView writeEnd(HttpServletRequest request,ModelAndView mav) {
      
      /*HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
      
      if(loginuser != null) {
         login_userid = loginuser.getUserid();
         // login_userid 는 로그인 되어진 사용자의 userid 이다.
      }
      */
      Map<String,String> paraMap = new HashMap<>();
      int n =0;
      
      String name_es = request.getParameter("name_es"); // 이름들
      String receiver_es = request.getParameter("receiver_es"); // 번호들
      String text = request.getParameter("DOC_TEXT");
      
      String receiverArr[] = receiver_es.split(",");
      String nameArr[] = name_es.split(",");
      
      for(int i =0; i<receiverArr.length; i++) {
         String receiver = receiverArr[i];
         String name = nameArr[i];
         
         paraMap.put("receiver", receiver);
         paraMap.put("name", name);
         paraMap.put("text", text);
         paraMap.put("login_userid", "1004");// 현재 로그인된 아이디를 1004라고 가정한다.
         
         // 쪽지작성 insert
          n = service.writeEnd(paraMap); 
         // n 이 1 이라면 정상적으로 insert
         // n 이 0 이라면 insert안됌
         
      }
      if(n == 1) {
         // 성공
         mav.setViewName("message/inbox.tiles2");
         
      }
      else {
         // 실패
         String message = "쪽지 전송에 실패하였습니다.";
         String loc = "javascript:history.back()";
         
         mav.addObject("message", message);
         mav.addObject("loc", loc);
         
         mav.setViewName("msg");
         System.out.println("시퓨ㅐ");
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
          String nameMaj = service.getNameMaj(majseq);
          
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
     
   
}
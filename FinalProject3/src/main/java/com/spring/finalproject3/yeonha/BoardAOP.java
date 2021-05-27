package com.spring.finalproject3.yeonha;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.finalproject3.common.MyUtil;
import com.spring.finalproject3.yeonha.InterBoardService;


//=== #53. 공통관심사 클래스(Aspect 클래스)생성하기 === //
// AOP(Aspect Oriented Programming) ==> 관심 지향 프로그래밍
/*
		주업무 		-- 		     보조업무(Aspect 클래스)
	(게시판글쓰기			
	,장바구니담기			(주업무를 하기전에 로그인유무 검사를 해야하는 것이 공통업무)
	,좋아요,싫어요
	,주문하기 등등)		
	
		주업무 		-- 		     보조업무(Aspect 클래스)
	(게시판글쓰기 완료
	,댓글쓰기 완료			(주업무가 완료되어진 이후에 회원의 포인트를 증가시켜주는 것이 공통업무)
	,주문완료	
		
*/
@Aspect 	// 공통관심사 클래스(Aspect 클래스)로 등록된다
@Component  // bean 으로 등록된다.
public class BoardAOP {

	// ===== Before Advice(보조업무) 만들기 ====== // 
	/*
		주업무(<예: 글쓰기, 글수정, 댓글쓰기 등등>)를 실행하기 앞서서  
		이러한 주업무들은 먼저 로그인을 해야만 사용가능한 작업이므로
		주업무에 대한 보조업무<예: 로그인 유무검사> 객체로 로그인 여부를 체크하는
		관심 클래스(Aspect 클래스)를 생성하여 포인트컷(주업무)과 어드바이스(보조업무)를 생성하여
		동작하도록 만들겠다.
	*/  
	
	// === Pointcut(주업무)을 설정해야 한다. === // 
	//     Pointcut 이란 공통관심사를 필요로 하는 메소드를 말한다.
	@Pointcut("execution(public * com.spring..*Controller.requiredLogin_*(..))")
	public void requiredLogin() {}
	
	
	// === Before Advice(공통관심사, 보조업무)를 구현한다. === //
	// 로그인 유무 검사를 하는 메소드 작성하기 
	@Before("requiredLogin()")
	public void loginCheck(JoinPoint joinPoint) {
		// JoinPoint joinPoint 는 포인트컷 되어진 주업무의 메소드이다. 
		
		// 로그인 유무를 확인하기 위해서는 request를 통해 session을 얻어와야 한다.
		HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0]; // 주업무 메소드의 첫번째 파라미터를 얻어오는 것이다.
		HttpServletResponse response = (HttpServletResponse) joinPoint.getArgs()[1]; // 주업무 메소드의 두번째 파라미터를 얻어오는 것이다.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginuser") == null) {
			String message = "먼저 로그인 하세요~~~";
	        String loc = request.getContextPath()+"/login.action";
	         
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        // >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업 만들기 <<< // 
	        // === 현재 페이지의 주소(URL) 알아오기 === 
	        String url = MyUtil.getCurrentURL(request);
	        // System.out.println("~~~ 확인용 url => " + url);
	        // add.action
	        
	        session.setAttribute("goBackURL", url); // 세션에 url 정보를 저장시킨다
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
	        
	        try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
	        
		}
		
	}
	
	
	
	
	
	
}

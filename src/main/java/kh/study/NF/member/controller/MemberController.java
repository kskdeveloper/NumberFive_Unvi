package kh.study.NF.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.study.NF.config.MemRole;
import kh.study.NF.mail.service.MailService;
import kh.study.NF.member.service.MemberService;
import kh.study.NF.member.vo.MemberVO;

// by 유빈

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Autowired
	private MailService mailService;
	
	//암호화 작업 1-주석풀기 
	@Autowired
	private PasswordEncoder encoder;
//-------------------------------------------------------------------------------------------///	
	
	//회원가입(shop )
	@PostMapping("/join")
	public String join(@Valid MemberVO memberVO, BindingResult bindingResult, Model model) {
		// !! validation 체크 (데이터 유효성 검증)
		if (bindingResult.hasErrors()) {// 바인딩하는데 오류가 생겼니?: 결과는 true/false
			model.addAttribute("memberVO", memberVO);//회원가입 실패시 입력 데이터 값 유지.
			System.out.println("@@@@@@@@@@@@@@@@@@@@ 회원가입 유효성체크 >>> error발생   @@@@@@@@@@@@@@@@@@@");
			System.out.println("지금 바인딩 오류의 상태는? " + bindingResult.hasErrors());//true
			//어떤 오류인지 확인
			 List<ObjectError> list =  bindingResult.getAllErrors();
             for(ObjectError e : list) {
                  System.out.println(e.getDefaultMessage());
             }
		}
		memberVO.setMemRole(MemRole.STUDENT.toString());
		//암호화 작업2 -주석풀기 
		memberVO.setMemPw(encoder.encode(memberVO.getMemPw()));
		//회원가입 
		memberService.join(memberVO);
		return "content/common/join_result";
	}
//-------------------------------------------------------------------------------------------///	
	// by 유빈 : 학생정보시스템의 첫 화면 로그인 페이지입니다.
	// --> stu컨트롤러에서 학생,교수,교직원 모두 로그인해야해서 공통사항은 (common폴더)여기로 옮겼어!!)
	// 첫 화면 경로 : http://localhost:8081/member/homeLogin
	@GetMapping("/homeLogin")
	public String homeLogin(MemberVO memberVO, boolean isLoginFail, Model model ) {
		//-----로그인 성공 및 실패 여부를 html에 데이터 전달하기-------//
		System.out.println("____________지금 로그인 실패니???_________" + isLoginFail);
		model.addAttribute("isLoginFail",isLoginFail);
		return "content/common/home_Login"; //by 유빈 :로그인페이지는 공통이라 common폴더 아래 login으로 파일 만들었어!!
	}
//-------------------------------------------------------------------------------------------///	
	// 로그인성공시,로그인실패시 -> 로그인페이지로 이동
	// 스프링 시큐리티 config에서 설정한 경로대로 보내준다.
	@GetMapping("/loginResult")
	public String loginResult() {
		System.out.println("로그인 결과");
		return "content/common/login_result";
	}	
//-------------------------------------------------------------------------------------------///	
	// 이메일로 비밀번호 찾기 (ajax.ver) -> 모달 ajax사용 (ajaxlogin이라는 버튼을 클릭시)
	//@ResponseBody //ajax사용할때(단,리턴값은 필요한 데이터만! html페이지가 아님!)
	@PostMapping("/checkAndSendEmail")
	public String ajaxLogin( MemberVO memberVO) {
		//비밀번호 찾기 쿼리문이용해서 회원존재확인하기.
		MemberVO loginInfo = memberService.findPw(memberVO);
		
		// 로그인 정보 시큐리티로(암호화) 이용
		if(loginInfo != null) {
			System.out.println("____________현재 로그인 상태는?______________ "+ loginInfo);
			
			// ----------------------------세션으로 로그인 일때 ,session.setAttribute("loginInfo", loginInfo);
			// 시큐리티로 로그인 일때,
			// 이메일로 임시비밀번호 전송하기.
			// 수신 대상을 담을 arrayList 생성
	    	ArrayList<String> toUerList = new ArrayList<>();
	    	
	    	//수신 대상 추가
	    	toUerList.add(memberVO.getMemEmail());//조회한 회원의 이메일가져오기. get
	    	mailService.sendMdail();
	    	System.out.println("________________ 이메일 발송 성공_________________");
	    	
	    	// 여기까지 11/11 완료된 상태
	    	//////////////////////////////////////////////////////////////////////////////////////////////////////
	    	// 여기서부터 프로젝트 다시 시작하면된다. 현재상태 : 업데이트쿼리문이 실행되지 않는 상태이다...
	    	// 그러면 회원가입을 진행하고 시큐리티 암호화작업들어가면서 비밀번호 생성해보자. 
	    	
	    	// 아래처럼 암호화된 비밀번호를 넣어서 업데이트해야한다.
			 memberVO.setMemPw(encoder.encode(memberVO.getMemPw()));

	    	// 임시발급비밀번호로 업데이트하기
	    	memberService.updatePw(memberVO);
	    	return "content/common/reLogin";
			
		} 
		System.out.println("_____________현재 로그인 상태는?___________________ "+ loginInfo);
		// 바로 loginInfo를 주지않고 삼항연산자 사용한다
		//return loginInfo == null? false :true;//자료형 boolean
		return "redirect:/member/afterLogin";
	}
//-------------------------------------------------------------------------------------------///	
	
	// ajax로 이메일 임시비밀번호 발급 후 이동 페이지 
	// 이전 shop에서 이름만->ajaxlogin 메소드 가져온것임.
	@GetMapping("/afterLogin")
	public String afterLogin1(boolean isLoginFail, Model model) {
		//-----로그인 성공 및 실패 여부를 html에 데이터 전달하기-------//
		System.out.println("_______________로그인 성공시 false값 떠라 -->" + isLoginFail);
		model.addAttribute("isLoginFail",isLoginFail);
		
		return "content/common/after_Login";
	}
//-------------------------------------------------------------------------------------------///	
	
	//로그인
	@PostMapping("/stuLogin")
	public String afterLogin2(boolean isLoginFail, Model model) {
		//-----로그인 성공 및 실패 여부를 html에 데이터 전달하기-------//
		//false면 로그인성공-fail이면 로그인 실패
		System.out.println("@@@@@@@@@@@@@@@@@@@" + isLoginFail);
		model.addAttribute("isLoginFail",isLoginFail);
		
		return "content/common/afterLogin";
	}
//-------------------------------------------------------------------------------------------//	
	// 나의 정보관리 클릭시,
	@GetMapping("/myinfo")
	public String myinfo(String memNo, Model model,Authentication authentication) {
		//시큐리티 사용했으므로 로그인한 유저의 정보를 가져온다.
		User user = (User)authentication.getPrincipal();
		//로그인한 사람의 아이디,비밀번호,권한정보 데이터만 가져올수있다.
		System.out.println("id = " + user.getUsername());
		//System.out.println("pw = " + encoder.encode(user.getPassword()));
		
		//회원상세정보조회
		model.addAttribute("memInfo", memberService.selectMemberDetail(memNo));
		
		return "content/common/my_info";
	}

	//상세조회 후 회원정보수정
	@PostMapping("/updateMemInfo")
	public String updateMemInfo() {
		return "redirect:/member/myInfo";
	}
//-------------------------------------------------------------------------------------------//
	
	// 관리자모드 - 회원등록양식페이지이동
	@GetMapping("/regMemForm")
	public String regMemForm(MemberVO memberVO, boolean isLoginFail, Model model ) {
		//-----로그인 성공 및 실패 여부를 html에 데이터 전달하기-------//
		System.out.println("____________지금 로그인 실패니???_________" + isLoginFail);
		model.addAttribute("isLoginFail",isLoginFail);
		return "content/admin/reg_mem";
	}
	
	//관리자모드 - 회원등록 실제 페이지 이동
	@PostMapping("/regMem")
	public String regMem(@Valid MemberVO memberVO, BindingResult bindingResult, Model model) {
		// !! validation 체크 (데이터 유효성 검증)
		if (bindingResult.hasErrors()) {// 바인딩하는데 오류가 생겼니?: 결과는 true/false
			model.addAttribute("memberVO", memberVO);//회원가입 실패시 입력 데이터 값 유지.
			System.out.println("@@@@@@@@@@@@@@@@@@@@ 회원가입 유효성체크 >>> error발생   @@@@@@@@@@@@@@@@@@@");
			System.out.println("지금 바인딩 오류의 상태는? " + bindingResult.hasErrors());//true
			//어떤 오류인지 확인
			 List<ObjectError> list =  bindingResult.getAllErrors();
             for(ObjectError e : list) {
                  System.out.println(e.getDefaultMessage());
             }
		}
		memberVO.setMemRole(MemRole.STUDENT.toString());
		//암호화 작업2 -주석풀기 
		memberVO.setMemPw(encoder.encode(memberVO.getMemPw()));
		//회원가입 
		memberService.join(memberVO);
		return "redirect:/member/afterLogin";
	}	
	
	
	
	
	

	
	
//-------------------------------------------------------------------------------------------//
	// 로그인 실패시
	@GetMapping("/loginFail")
	public String loginFail( ) {
		
		return "content/member/login";
	}
	
	// 접근거부시
	@GetMapping("/accessDenied")
	public String accessDenied( ) {
		
		return "content/member/accessDenied";
	}
}

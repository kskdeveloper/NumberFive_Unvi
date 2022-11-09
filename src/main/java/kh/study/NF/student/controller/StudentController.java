package kh.study.NF.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.NF.config.student.ApplyCode;
import kh.study.NF.dept.service.DeptService;
import kh.study.NF.dept.vo.DeptVO;
import kh.study.NF.emp.vo.DeptManageVO;
import kh.study.NF.student.service.StudentService;


@Controller
@RequestMapping("/stu")
public class StudentController {

	@Resource(name = "studentService")
	private StudentService studentService;
	
	@Resource(name = "deptService")
	private DeptService deptService;
	
	//by수경 학생이 전공학과를 변경(전과)하는 페이지로 이동 메소드
	@GetMapping("/changeMajor")
	public String changeMajor(Model model, DeptManageVO deptManageVO) {
		//by수경 학생정보 쿼리문
		model.addAttribute("stuInfo", studentService.studentInfo());
		
		//by수경 변경할 전공대학, 전공학과 선택 쿼리문
		 model.addAttribute("deptList",deptService.selectDeptList());
		 model.addAttribute("collList",deptService.selectCollList());
		
		return "content/student/changeMajor";
	}
	
	//by수경 학생이 전공학과를 변경(전과)하는 페이지로 이동 메소드
	@GetMapping("/addMajor")
	public String addMajor(Model model, DeptManageVO deptManageVO) {
		//by수경 학생정보 쿼리문
		model.addAttribute("stuInfo", studentService.studentInfo());
		
		//by수경 변경할 전공대학, 전공학과 선택 쿼리문
		model.addAttribute("deptList",deptService.selectDeptList());
		model.addAttribute("collList",deptService.selectCollList());
		
		return "content/student/addMajor";
	}
	
	//by수경 변경할 전공대학 선택 시, 전공학과 선택 ajax (본인 현재 소속학과 제외)
	//추후 매개변수 DeptVO deptVO(collNo, stuNo)
	@ResponseBody
	@PostMapping("/getMajorAjax")
	public List<DeptVO> getMajorAjax(String collNo) {
		
		return studentService.DeptList(collNo);
	}
	
	//by수경 학생이 학교를 휴학신청하는 페이지로 이동
	@GetMapping("/takeOffUniv")
	public String takeOffUniv(Model model, DeptManageVO deptManageVO) {
		//by수경 학생정보 쿼리문
		model.addAttribute("stuInfo", studentService.studentInfo());
		return  "content/student/takeOffUniv";
	}
	
	//by수경 학생이 학교를 복학신청하는 페이지로 이동
	@GetMapping("/returnUniv")
	public String returnUniv(Model model, DeptManageVO deptManageVO) {
		//by수경 학생정보 쿼리문
		model.addAttribute("stuInfo", studentService.studentInfo());
		
		return  "content/student/returnUniv";
	}
	
	//by수경 학생이 학교를 복학 신청버튼 클릭 시 실행되는 메소드
	@ResponseBody
	@PostMapping("/applyReturnUnivAjax")
	public void applyReturnUniv(Model model, DeptManageVO deptManageVO) {
		//by수경 enum을 이용하여 value set
		deptManageVO.setApplyCode(ApplyCode.복학.toString());
		
		studentService.applyReturnUniv(deptManageVO);
		
		//return  "redirect:/stu/stuApplyList";
	}
	
	
	//by수경 학생이 휴학신청버튼 클릭 시 실행되는 메소드
	@ResponseBody
	@PostMapping("/applyTakeOffUnivAjax")
	public void applyTakeOffUniv(Model model, DeptManageVO deptManageVO) {
		//by수경 enum을 이용하여 value set
		deptManageVO.setApplyCode(ApplyCode.휴학.toString());
		
		studentService.applyTakeOffUniv(deptManageVO);
		
	}
	
	
	//by수경 학생이 학교를 전과 신청버튼 클릭 시 실행되는 메소드
	@ResponseBody
	@PostMapping("/applyChangeMajorAjax")
	public void applyChangeMajor(Model model, DeptManageVO deptManageVO) {
		//by수경 enum을 이용하여 value set
		deptManageVO.setApplyCode(ApplyCode.전과.toString());
		
		studentService.applyChangeMajor(deptManageVO);
		

	}
	//by수경 학생이 학교를 복수전공 신청버튼 클릭 시 실행되는 메소드
	@ResponseBody
	@PostMapping("/applyAddMajorAjax")
	public void applyAddMajor(Model model, DeptManageVO deptManageVO) {
		//by수경 enum을 이용하여 value set
		deptManageVO.setApplyCode(ApplyCode.복수전공.toString());
		
		studentService.applyAddMajor(deptManageVO);
		
		
	}
	
	//by수경 학생의 전공학과 변경(전과), 휴학신청, 복학신청, 복수전공 신청 현황
	@GetMapping("/stuApplyList")
	public String stuApplyList(Model model, String stuNo) {
		
		//신청 내역 목록
		List<DeptManageVO> applyList =  studentService.stuApplyList(stuNo);
		
		model.addAttribute("applyList", applyList);
		
		//신청 분류별 개수 구하기
		int changeMajor = 0, takeOff = 0, comeback = 0 , addMajor = 0;
		
		for(DeptManageVO applyInfo : applyList) {
			//전과
			if(applyInfo.getApplyCode().equals(ApplyCode.전과.toString()) ) {
				changeMajor++;
			}
			//휴학
			else if(applyInfo.getApplyCode().equals(ApplyCode.휴학.toString()) ) {
				takeOff++;			
			}
			//복학
			else if(applyInfo.getApplyCode().equals(ApplyCode.복학.toString()) ) {
				comeback++;
			}
			//복전
			else if(applyInfo.getApplyCode().equals(ApplyCode.복수전공.toString()) ) {
				addMajor++;
			}
		}
		
		//map으로 데이터 담아서 html로 넘기기
		Map<String, Integer> applyCodeMap = new HashMap<>();
		applyCodeMap.put("전과", changeMajor);
		applyCodeMap.put("휴학", takeOff);
		applyCodeMap.put("복학", comeback);
		applyCodeMap.put("복수전공", addMajor);
		
		model.addAttribute("applyCodeMap", applyCodeMap);
		
		return  "content/student/stuApplyList";
	}
	
}

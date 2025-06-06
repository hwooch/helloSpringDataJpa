package kr.ac.hansung.cse.hellospringdatajpa.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToProducts() {
        return "redirect:/products";
    }

    @GetMapping("/home")
    public String homePage(Authentication authentication, HttpSession session, Model model) {
        // 로그인된 사용자라면
        if (authentication != null) {
            // “loginShown” 플래그가 세션에 없는 경우 (첫 방문)
            if (session.getAttribute("loginShown") == null) {
                String username = ((User) authentication.getPrincipal()).getUsername();
                boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                String message = isAdmin
                        ? "운영자 " + username + "님 환영합니다"
                        : username + "님 환영합니다";

                // Model에 메시지 담기 → 뷰에서 한 번만 팝업 표시
                model.addAttribute("loginSuccessMsg", message);

                // 세션에 “보여줬음” 표시 (다음부터는 모델에 담지 않음)
                session.setAttribute("loginShown", true);
            }
        }
        return "home";
    }

}

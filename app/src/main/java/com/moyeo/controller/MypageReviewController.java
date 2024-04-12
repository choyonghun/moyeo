package com.moyeo.controller;

import com.moyeo.service.ReviewBoardService;
import com.moyeo.vo.Member;
import com.moyeo.vo.ReviewBoard;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("myreview")
public class MypageReviewController {

  private final static Log log = LogFactory.getLog(MypageReviewController.class);
  private final ReviewBoardService reviewBoardService;


  @GetMapping("scrap")
  public void scrapList(
      @RequestParam(required = false, defaultValue = "1")int pageNo,
      @RequestParam(required = false, defaultValue = "6")int pageSize,
      HttpSession session,
      Model model) {

    Member loginUser = (Member) session.getAttribute("loginUser");

    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = reviewBoardService.countScrapByMember(loginUser.getMemberId());
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    List<ReviewBoard> scrapList = reviewBoardService.scrapList(loginUser.getMemberId(), pageNo, pageSize);
    model.addAttribute("scrapList", scrapList);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);

//    log.debug(String.format("%d____________________________%d__________________________%s", pageNo, pageSize,countScrap));
  }
}



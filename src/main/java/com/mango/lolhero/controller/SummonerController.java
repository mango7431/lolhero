package com.mango.lolhero.controller;

        import com.mango.lolhero.dto.SummonerDTO;
        import com.mango.lolhero.service.SummonerService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    public String callSummonerByName(String summonerName, Model model){
        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);

        System.out.println(apiResult.toString());

        model.addAttribute("summoner",apiResult);

        return "selectPage";
    }

}

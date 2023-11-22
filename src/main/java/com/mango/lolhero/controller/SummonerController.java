package com.mango.lolhero.controller;

        import com.mango.lolhero.dto.*;
        import com.mango.lolhero.service.ChampionMasteryService;
        import com.mango.lolhero.service.MatchService;
        import com.mango.lolhero.service.SummonerService;
        import com.mango.lolhero.service.TierService;
        import lombok.RequiredArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.client.RestTemplate;

        import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SummonerController {

    private final SummonerService summonerService;

    private final MatchService matchService;

    private final ChampionMasteryService championMasteryService;

    private final TierService tierService;

    @PostMapping(value = "/summonerByName")
    public String callSummonerByName(String summonerName, Model model){
        summonerName = summonerName.replaceAll(" ","%20");

        SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);
        if(apiResult==null){
            model.addAttribute("summoner","null");
            return "errorPage";
        }else{
            List<String> match = matchService.MatchList(apiResult.getPuuid());
            List<ChampionMasteryDto> championMasteryDtos = championMasteryService.ChampionMastery(apiResult.getId());
            List<TierDto> tierDtos = tierService.getTier(apiResult.getId());
            log.info(tierDtos.toString());

            model.addAttribute("summoner",apiResult);
            model.addAttribute("champ",championMasteryDtos);
            if(tierDtos.isEmpty()){
                model.addAttribute("solo","none");
                model.addAttribute("flex","none");
            }else if(tierDtos.size()==1){
                if ("RANKED_SOLO_5x5".equals(tierDtos.get(0).getQueueType())) {
                    model.addAttribute("solo", tierDtos.get(0));
                    model.addAttribute("flex", "none");
                } else if ("RANKED_FLEX_SR".equals(tierDtos.get(0).getQueueType())) {
                    model.addAttribute("solo", "none");
                    model.addAttribute("flex", tierDtos.get(0));
                }
            }else if (tierDtos.size()==2){
                if ("RANKED_SOLO_5x5".equals(tierDtos.get(0).getQueueType())) {
                    model.addAttribute("solo",tierDtos.get(0));
                    model.addAttribute("flex",tierDtos.get(1));
                } else if ("RANKED_FLEX_SR".equals(tierDtos.get(0).getQueueType())) {
                    model.addAttribute("solo",tierDtos.get(1));
                    model.addAttribute("flex",tierDtos.get(0));
                }
            }
            return "selectPage";
        }
    }

}

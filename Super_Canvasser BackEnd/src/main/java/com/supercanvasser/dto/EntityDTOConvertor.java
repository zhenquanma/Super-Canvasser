package com.supercanvasser.dto;

import com.supercanvasser.bean.Campaign;
import com.supercanvasser.bean.Location;
import com.supercanvasser.bean.Question;
import com.supercanvasser.bean.User;

import java.util.List;

public class EntityDTOConvertor {
    public static Campaign convertToCampaign(CampaignDTO campaignDTO,
                                             List<Location> locationList,
                                             List<User> canvassers,
                                             List<User> managers,
                                             List<Question> questions){
        Campaign campaign = new Campaign();
        if(campaignDTO.getCampaignID() != null){
            campaign.setId(Integer.valueOf(campaignDTO.getCampaignID()));
        }
        campaign.setCampaignName(campaignDTO.getCampaignName());
        campaign.setStartDate(campaignDTO.getStartDate());
        campaign.setEndDate(campaignDTO.getEndDate());
        campaign.setVisitDuration(campaignDTO.getVisitDuration());
        campaign.setTalkingPoints(campaignDTO.getTalkingPoints());
        campaign.setManagers(managers);
        campaign.setCanvassers(canvassers);
        campaign.setLocations(locationList);
        campaign.setQuestions(questions);
        return campaign;

    }

//    public static CampaignDTO convertTOCampaignDTO(Campaign campaign){
//        CampaignDTO campaignDTO = new CampaignDTO();
//        campaignDTO.setCampaignId(campaign.getId().toString());
//        campaignDTO.setCampaignName(campaign.getCampaignName());
//        campaignDTO.setStartDate(campaign.getStartDate());
//        campaignDTO.setEndDate(campaign.getEndDate());
//        campaignDTO.setVisitDuration(campaign.getVisitDuration());
//        campaignDTO.setTalkingPoints(campaign.getTalkingPoints());
//
//        campaignDTO.setCanvasserList(campaign.getCanvassers());
//
//        campaignDTO.setManagerList(campaign.getManagers());
//        campaignDTO.setLocationsStr("");
//        return campaignDTO;
//
//    }
}

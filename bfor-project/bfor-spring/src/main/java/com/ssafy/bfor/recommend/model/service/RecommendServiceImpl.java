package com.ssafy.bfor.recommend.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ssafy.bfor.recommend.model.TravelInfoFavoriteDto;
import com.ssafy.bfor.recommend.model.mapper.RecommendMapper;
import com.ssafy.bfor.travelinfo.model.TravelInfoDto;

@Service
public class RecommendServiceImpl implements RecommendService{
	
	private RecommendMapper recommendMapper;

	public RecommendServiceImpl(RecommendMapper recommendMapper) {
		super();
		this.recommendMapper = recommendMapper;
	}

	@Override
	public TravelInfoFavoriteDto listPlaces(String userId) throws Exception {
		TravelInfoFavoriteDto travelInfo = new TravelInfoFavoriteDto();
		
		//넣을 장소들
		List<String> placesContentId = new ArrayList<>();
		// 유저 목록 불러오기
		List<String> userList = recommendMapper.userList();

//		로그인 아이디 제거
		userList.remove(userId);
//		System.out.println(userList); 
		List<String> loginUserPlaces = recommendMapper.getPlaceList(userId);
		System.out.println("loginUser" + loginUserPlaces.toString());
		int standard = 4;
		String favUser = "";
		int maxCount = 0;
		
		for (String user : userList) {
			List<String> places = recommendMapper.getPlaceList(user);
//			System.out.println(places.toString());
//			hashset을 이용해 개수 구하기 
			int count = getIntersectionCount(loginUserPlaces, places);
//			System.out.println("count :" + count);
//			개수가 standard보다 크면 userLogin의 장소들과 겹치지 않는 장소들 반환
			if (count > standard) {
				List<String> nonCommonElements = getNonCommonElements(loginUserPlaces, places);
//				System.out.println("nonCommonElements" + nonCommonElements.toString());
				placesContentId.addAll(nonCommonElements);
			}
			if (count > maxCount) {
				maxCount = count;
				favUser = user;
			}
		}
		
//		가장 좋아하는 타입 반환
		int key = recommendMapper.getUserFavoriteType(userId);
		String favCategory = getType(key);
		
//		반환된 장소 5개 미만이라면
		if (placesContentId.size() < 5) {
//			가장 좋아하는 타입 장소얻기
			List<String> favContentTypeIdPlacces = recommendMapper.getFavContentTypePlaces(key);
			placesContentId.addAll(favContentTypeIdPlacces);
		}
		System.out.println(placesContentId.toString());
		
		//추천 장소 contentId 받았으니 정보들 받기
		List<TravelInfoDto>	list = new ArrayList<>();
		for (String place : placesContentId) {
			TravelInfoDto info = recommendMapper.getInfo(place);
			list.add(info);
		}
		
		travelInfo.setUserId(favUser == null ? "": favUser);
		travelInfo.setCount(maxCount);
		travelInfo.setFavCategory(favCategory);
		travelInfo.setPlaces(list);
		return travelInfo;
	}
	
	
	
	public int getIntersectionCount(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);
        // Retain only the elements in set1 that are contained in set2
        set1.retainAll(set2);
        // The size of set1 is now the number of common elements
        return set1.size();
    }
	
	// 리스트 2에서 리스트 1과 겹치지 않는 요소를 반환하는 메서드
    public List<String> getNonCommonElements(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);  // 리스트 1을 Set으로 변환
        List<String> nonCommonElements = new ArrayList<>();  // 결과를 저장할 리스트

        for (String element : list2) {
            if (!set1.contains(element)) {  // 리스트 1에 없는 요소만 추가
                nonCommonElements.add(element);
            }
        }

        return nonCommonElements;  // 결과 반환
    }
    
    public String getType(int key) {
        if (key == 12) {
            return "관광지";
        } else if (key == 14) {
            return "문화시설";
        } else if (key == 15) {
            return "축제공연행사";
        } else if (key == 25) {
            return "여행코스";
        } else if (key == 28) {
            return "레포츠";
        } else if (key == 32) {
            return "숙박";
        } else if (key == 38) {
            return "쇼핑";
        } else if (key == 39) {
            return "음식";
        } else {
            return "알 수 없는 타입";
        }
    }
}

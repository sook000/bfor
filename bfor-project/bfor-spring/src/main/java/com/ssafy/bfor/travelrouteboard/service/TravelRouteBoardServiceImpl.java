package com.ssafy.bfor.travelrouteboard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.bfor.travelinfo.model.TravelInfoDto;
import com.ssafy.bfor.travelrouteboard.mapper.TravelRouteBoardMapper;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardListDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardOrderDto;
import com.ssafy.bfor.travelrouteboard.model.TravelRouteBoardThumbnailDto;

@Service
public class TravelRouteBoardServiceImpl implements TravelRouteBoardService {

	TravelRouteBoardMapper travelRouteBoardMapper;

	@Autowired
	public TravelRouteBoardServiceImpl(TravelRouteBoardMapper travelRouteBoardMapper) {
		this.travelRouteBoardMapper = travelRouteBoardMapper;
	}

	@Override
	public Integer insert(HashMap<String, Object> map) {
		travelRouteBoardMapper.insert(map);

		List<LinkedHashMap<String, Object>> infoList = (List<LinkedHashMap<String, Object>>) map.get("travelInfoList");

		if (infoList.size() > 0) {
			List<Integer> contentIdList = new ArrayList<>();
			for (LinkedHashMap<String, Object> travelInfoMap : infoList) {
				TravelInfoDto travelInfo = convertMapToTravelInfoDto(travelInfoMap);
				contentIdList.add(travelInfo.getContentId());
			}

			if (infoList != null && !infoList.isEmpty()) {
				int trbNo = travelRouteBoardMapper.getMaxTrbId((String) map.get("userId"));
				int listSize = infoList.size();
				for (int i = 0; i < listSize; i++) {
					Map<String, Object> newMap = new HashMap<>();
					newMap.put("contentId", contentIdList.get(i));
					newMap.put("trbNo", trbNo);
					newMap.put("order", i + 1);
					travelRouteBoardMapper.insertTravelOrder(newMap);
				}

				Double[][] graph = new Double[listSize][listSize];
				for (int i = 0; i < listSize; i++) {
					for (int j = 0; j < listSize; j++) {
						if (i != j) {
							Double Lat1 = (Double) infoList.get(i).get("latitude");
							Double Lon1 = (Double) infoList.get(i).get("longitude");
							Double Lat2 = (Double) infoList.get(j).get("latitude");
							Double Lon2 = (Double) infoList.get(j).get("longitude");
							graph[i][j] = getDistance(Lat1, Lon1, Lat2, Lon2);
						}
					}
				}
				dijkstra(graph, listSize);
			}
		}

		return 1;
	}

	@Override
	public TravelRouteBoardListDto getList(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		String userId = map.get("userId");
		param.put("key", key == null ? "" : key);
		param.put("userId", userId == null? "" : userId );
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "user_id");
		
		System.out.println(map);
		List<TravelRouteBoardDto> list = travelRouteBoardMapper.getList(param);

		int totalArticleCount = travelRouteBoardMapper.getTotalListCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		TravelRouteBoardListDto travelRouteBoardListDto = new TravelRouteBoardListDto();
		travelRouteBoardListDto.setList(list);
		travelRouteBoardListDto.setCurrentPage(currentPage);
		travelRouteBoardListDto.setTotalPageCount(totalPageCount);

		return travelRouteBoardListDto;
	}

	@Override
	public TravelRouteBoardDto read(int trbNo) {
		return travelRouteBoardMapper.read(trbNo);
	}

	@Override
	public Integer update(HashMap<String, String> map) {
		return travelRouteBoardMapper.update(map);
	}

	@Override
	public Integer delete(int trbNo) {
		return travelRouteBoardMapper.delete(trbNo);
	}

	@Override
	public void insertTravelList(List<Object> list) {
		travelRouteBoardMapper.insertTravelList(list);
	}

	@Override
	public Integer getMaxTrbId(String userId) {
		return travelRouteBoardMapper.getMaxTrbId(userId);
	}

	@Override
	public List<TravelInfoDto> getOrderInfo(int trbNo) {
		return travelRouteBoardMapper.getOrderInfo(trbNo);
	}


	private TravelInfoDto convertMapToTravelInfoDto(LinkedHashMap<String, Object> map) {
		TravelInfoDto travelInfoDto = new TravelInfoDto();
		travelInfoDto.setContentId((Integer) map.get("contentId"));

		
		travelInfoDto.setLatitude(Math.round((Double) map.get("latitude") * 100.0) / 100.0);
		travelInfoDto.setLongitude((Double) map.get("longitude"));
		return travelInfoDto;
	}

	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		int EARTH_RADIUS = 6371; // 지구 반지름 (단위: km)
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = EARTH_RADIUS * c; // Distance in m *1000은 m ,빼면 km
		return d;
	}

	@Override
	public Integer getNormalDistance(List<TravelInfoDto> list) {
		if (list.size() <= 1)  return 0;
		else {
			double distance = 0;
			for (int i = 1; i < list.size(); i++) {
				distance += getRoundValue(getDistance(list.get(i-1).getLatitude(), list.get(i-1).getLongitude(), list.get(i).getLatitude(), list.get(i).getLongitude()));
				System.out.println(distance);
			}
			return (int) distance;
		}
	}
	
	@Override
	public Integer getDijkstraDistance(List<TravelInfoDto> list) {
		if (list.size() <= 1)  return 0;
		else {
			int listSize = list.size();
			Double[][] graph = new Double[listSize][listSize];
			for (int i = 0; i < listSize; i++) {
				for (int j = 0; j < listSize; j++) {
					if (i != j) {
						Double Lat1 = (Double) list.get(i).getLatitude();
						Double Lon1 = (Double) list.get(i).getLongitude();
						Double Lat2 = (Double) list.get(j).getLatitude();
						Double Lon2 = (Double) list.get(j).getLongitude();
						graph[i][j] = getRoundValue(getDistance(Lat1, Lon1, Lat2, Lon2));
					}
				}
			}
			int distance = (int) dijkstra(graph, listSize);
			return (int) distance;
		}
	}
	
	private int dijkstra(Double[][] graph, int listSize) {
	    // Comparator를 사용하여 PriorityQueue를 생성
	    PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
	    pq.add(new double[] {0, 0}); // 시작점과 거리
	    Double[] distance = new Double[listSize];
	    Arrays.fill(distance, Double.MAX_VALUE);
	    distance[0] = 0.0; // 시작점의 거리는 0
	
	    while (!pq.isEmpty()) {
	        double[] now = pq.poll();
	        int currentNode = (int) now[0];
	        double currentDistance = now[1];
//	        System.out.println(currentNode);
	        if (currentDistance > distance[currentNode]) {
	            continue;
	        }
	        for (int i = 0; i < listSize; i++) {
	            if (currentNode != i && graph[currentNode][i] != null) {
	                double nextDistance = currentDistance + graph[currentNode][i];
	                if (distance[i] > nextDistance) {
	                    distance[i] = nextDistance;
	                    pq.add(new double[] {i, nextDistance});
	                }
	            }
	        }
	    }
	    double totalDist = 0;
	    for (int i = 1; i < listSize; i++) {
	    	totalDist = Math.max(totalDist, distance[i]);
	    };
	    // 거리 배열 출력 (디버그용)
	    System.out.println(Arrays.toString(distance));
	    return (int) totalDist;
	}
	
	@Override
	public List<TravelInfoDto> getDijkstraDistanceList(List<TravelInfoDto> list) {
		if (list.size() <= 1)  return list;
		else {
			int listSize = list.size();
			Double[][] graph = new Double[listSize][listSize];
			for (int i = 0; i < listSize; i++) {
				for (int j = 0; j < listSize; j++) {
					if (i != j) {
						Double Lat1 = (Double) list.get(i).getLatitude();
						Double Lon1 = (Double) list.get(i).getLongitude();
						Double Lat2 = (Double) list.get(j).getLatitude();
						Double Lon2 = (Double) list.get(j).getLongitude();
						graph[i][j] = getRoundValue(getDistance(Lat1, Lon1, Lat2, Lon2));
					}
				}
			}
			List<TravelInfoDto> outputList = dijkstraList(graph, listSize, list);
			return outputList;
		}
	}
	private List<TravelInfoDto> dijkstraList(Double[][] graph, int listSize, List<TravelInfoDto> list) {
	    // Comparator를 사용하여 PriorityQueue를 생성
	    PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
	    pq.add(new double[] {0, 0}); // 시작점과 거리
	    Double[] distance = new Double[listSize];
	    Arrays.fill(distance, Double.MAX_VALUE);
	    distance[0] = 0.0; // 시작점의 거리는 0
	    List<TravelInfoDto> outputList = new ArrayList<>();
	    while (!pq.isEmpty()) {
	        double[] now = pq.poll();
	        int currentNode = (int) now[0];
	        double currentDistance = now[1];
//	        System.out.println(currentNode);
	        if (currentDistance > distance[currentNode]) {
	            continue;
	        }
	        outputList.add(list.get(currentNode));
	        for (int i = 0; i < listSize; i++) {
	            if (currentNode != i && graph[currentNode][i] != null) {
	                double nextDistance = currentDistance + graph[currentNode][i];
	                if (distance[i] > nextDistance) {
	                    distance[i] = nextDistance;
	                    pq.add(new double[] {i, nextDistance});
	                }
	            }
	        }
	    }

	    // 거리 배열 출력 (디버그용)
	    System.out.println(outputList);
		return outputList;
	}
	
	private double getRoundValue(double v) {
		return (Math.round(v * 100.0) / 100.0);
	}


	@Override
	public List<TravelRouteBoardThumbnailDto> getThumbnailList(HashMap<String, String> map) {
		return travelRouteBoardMapper.getThumbnailList(map);
	}

}

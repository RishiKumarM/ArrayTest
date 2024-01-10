package com.soccertennisgame.soccertennis.AddCricketSoccerTennis;

import com.soccertennisgame.soccertennis.Model.matchDetailsModel;
import com.soccertennisgame.soccertennis.Repository.matchDetailsModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AutoCricketData {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private matchDetailsModelRepository matchDetailsModelRepository;

    @Value("${api.cricket}")
    private String apiCricket;
    @Value("${api.soccer}")
    private String apiSoccer;
    @Value("${api.tennis}")
    private String apiTennis;
    private static final SimpleDateFormat ISO8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("MMM d yyyy h:mma (z)");
    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TARGET_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy, h:mm:ss a");

    private String apiDMDCricket = "http://marketsarket.qnsports.live/getcricketmatches";
    @Value("${apiDMD.soccer}")
    private String apiDMDSoccer;
    @Value("${apiDMD.tennis}")
    private String apiDMDTennis;

@Scheduled(fixedDelay = 180 * 1000)
    public void fetchDataFormBetFairForCricket() {

        List<matchDetailsModel> matchDetailsModelList = new ArrayList<>();

        LinkedHashMap<String, Object> dataMap = fetchDataFromApiCricket();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (Objects.equals(key, "data")) {

                Object innerMap1 = entry.getValue();
                if (innerMap1 instanceof ArrayList) {
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerMap1;
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        matchDetailsModel m = matchDetailsModelRepository.findByEventId((String) t.get("eventId"));
                        if (m != null) {
                            System.out.println(("Data already exist !! "));
                            continue;
                        } else {

                            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        matchDetailsModel lbr = new matchDetailsModel();

                        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                        lbr.setEventId((String) t.get("eventId"));
                        lbr.setEventTypeId((Integer) t.get("EventTypeId"));
                        lbr.setEventTypeName((String) t.get("EventTypeName"));
                        lbr.setGameStatus(0);
                        lbr.setSeriesId((String) t.get("SeriesId"));
                        lbr.setSeriesName((String) t.get("SeriesName"));
                        lbr.setOddsSource("BETFAIR");
                        lbr.setSource("BETFAIR");
                        lbr.setWinnerTeamId(9999999);
                        lbr.setWinnTeamName((String) t.get(""));
                        lbr.setAddDate(dateTime);
                        String eDate = (String) t.get("eventDate");
                        lbr.setEventDate(eDate != null && !eDate.isEmpty() ? Instant.parse(eDate).atZone(ZoneId.of("UTC")).toLocalDateTime() : null);
                        lbr.setEventName((String) t.get("eventName"));
                        lbr.setIsAddedToMarket(0);
                        lbr.setIsInPlay(0);
                        lbr.setMarketId((String) t.get("marketId"));
                        lbr.setMarket_runner_count((Integer) t.get("market_runner_count"));
                        lbr.setRunnerName1((String) t.get("runnerName1"));
                        lbr.setRunnerName2((String) t.get("runnerName2"));
                        lbr.setSelectionId1((Integer) t.get("selectionId1"));
                        lbr.setSelectionId2((Integer) t.get("selectionId2"));
                        String runnerName3 = (String) t.get("runnerName3");
                        if (runnerName3 != null) {
                            lbr.setRunnerName3((String) t.get("runnerName3"));
                            lbr.setSelectionId3((Integer) t.get("selectionId3"));
                        } else {
                            lbr.setRunnerName3(null);
                            lbr.setSelectionId3(0);
                        }
                        lbr.setMatch_type((String) t.get("match_type"));
                        lbr.setSrno((Integer) t.get("srno"));
                        String sDate = (String) t.get("startDate");
                        lbr.setStartDate(sDate != null && !sDate.isEmpty() ? Instant.parse(sDate).atZone(ZoneId.of("UTC")).toLocalDateTime() : null);
                            try {
                                Date date = iso8601Format.parse(sDate);
                                SimpleDateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy, h:mm:ss a");
                                String formattedDate = targetFormat.format(date);
                                lbr.setStartDateIST(formattedDate);
                                lbr.setMatchTimeIST(formattedDate);
                            } catch (ParseException e) {
                                continue;
                            }

                            matchDetailsModelList.add(lbr);
                    }
                }

                }

            }

        }

        matchDetailsModelRepository.saveAll(matchDetailsModelList);
        System.out.println(("BetFair_Cricket"));

    }
    @Scheduled(fixedDelay = 180 * 1000)
    public void fetchDataFormBetFairForSoccer() {

        List<matchDetailsModel> matchDetailsModelList = new ArrayList<>();

        LinkedHashMap<String, Object> dataMap = fetchDataFromApiSoccer();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (Objects.equals(key, "data")) {

                Object innerMap1 = entry.getValue();
                if (innerMap1 instanceof ArrayList) {
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerMap1;
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        matchDetailsModel m = matchDetailsModelRepository.findByEventId((String) t.get("eventId"));
                        if (m != null) {
                            System.out.println(("Data already exist !! "));
                            continue;
                        } else {

                            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            matchDetailsModel lbr = new matchDetailsModel();

                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                            lbr.setEventId((String) t.get("eventId"));
                            lbr.setEventTypeId((Integer) t.get("EventTypeId"));
                            lbr.setEventTypeName((String) t.get("EventTypeName"));
                            lbr.setGameStatus(0);
                            lbr.setSeriesId((String) t.get("SeriesId"));
                            lbr.setSeriesName((String) t.get("SeriesName"));
                            lbr.setOddsSource("BETFAIR");
                            lbr.setSource("BETFAIR");
                            lbr.setWinnerTeamId(9999999);
                            lbr.setWinnTeamName((String) t.get(""));
                            lbr.setAddDate(dateTime);
                            String eDate = (String) t.get("eventDate");
                            lbr.setEventDate(eDate != null && !eDate.isEmpty() ? Instant.parse(eDate).atZone(ZoneId.of("UTC")).toLocalDateTime() : null);
                            lbr.setEventName((String) t.get("eventName"));
                            lbr.setIsAddedToMarket(0);
                            lbr.setIsInPlay(0);
                            lbr.setMarketId((String) t.get("marketId"));
                            lbr.setMarketName((String) t.get("marketName"));
                            lbr.setMarketType((String) t.get("marketType"));
                            lbr.setMarket_runner_count((Integer) t.get("market_runner_count"));
                            lbr.setRunnerName1((String) t.get("runnerName1"));
                            lbr.setRunnerName2((String) t.get("runnerName2"));
                            lbr.setSelectionId1((Integer) t.get("selectionId1"));
                            lbr.setSelectionId2((Integer) t.get("selectionId2"));
                            String runnerName3 = (String) t.get("runnerName3");
                            if (runnerName3 != null) {
                                lbr.setRunnerName3((String) t.get("runnerName3"));
                                lbr.setSelectionId3((Integer) t.get("selectionId3"));
                            } else {
                                lbr.setRunnerName3(null);
                                lbr.setSelectionId3(0);
                            }
                            lbr.setMatch_type((String) t.get("match_type"));
                            lbr.setSrno((Integer) t.get("srno"));
                            String sDate = (String) t.get("startDate");
                            lbr.setStartDate(sDate != null && !sDate.isEmpty() ? Instant.parse(sDate).atZone(ZoneId.of("UTC")).toLocalDateTime() : null);
                            try {
                                Date date = iso8601Format.parse(sDate);
                                SimpleDateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy, h:mm:ss a");
                                String formattedDate = targetFormat.format(date);
                                lbr.setStartDateIST(formattedDate);
                                lbr.setMatchTimeIST(formattedDate);
                            } catch (ParseException e) {
                                continue;
                            }

                            matchDetailsModelList.add(lbr);
                        }
                    }

                }

            }

        }

    matchDetailsModelRepository.saveAll(matchDetailsModelList);
        System.out.println(("BetFair_Soccer"));

    }

    @Scheduled(fixedDelay = 180 * 1000)
    public void fetchDataFormBetFairForTennis() {

        List<matchDetailsModel> matchDetailsModelList = new ArrayList<>();

        LinkedHashMap<String, Object> dataMap = fetchDataFromApiTennis();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (Objects.equals(key, "data")) {

                Object innerMap1 = entry.getValue();
                if (innerMap1 instanceof ArrayList) {
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerMap1;
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        matchDetailsModel m = matchDetailsModelRepository.findByEventId((String) t.get("eventId"));
                        if (m != null) {
                            System.out.println(("Data already exist !! "));
                        } else {

                            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            matchDetailsModel lbr = new matchDetailsModel();

                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                            lbr.setEventId((String) t.get("eventId"));
                            int eventTypeId = 0;
                            Object eventIdObject = t.get("EventTypeId");

                            if (eventIdObject != null) {
                                if (eventIdObject instanceof Integer) {
                                    eventTypeId = (Integer) eventIdObject;
                                } else if (eventIdObject instanceof String) {
                                    try {
                                        eventTypeId = Integer.parseInt((String) eventIdObject);
                                    } catch (NumberFormatException ignored) {
                                    }
                                }
                            }

                            lbr.setEventTypeId(eventTypeId);
                            lbr.setEventTypeName((String) t.get("EventTypeName"));
                            lbr.setGameStatus(0);
                            lbr.setSeriesId((String) t.get("SeriesId"));
                            lbr.setSeriesName((String) t.get("SeriesName"));
                            lbr.setOddsSource("BETFAIR");
                            lbr.setSource("BETFAIR");
                            lbr.setWinnerTeamId(9999999);
                            lbr.setWinnTeamName((String) t.get(""));
                            lbr.setAddDate(dateTime);
                            String eDate = (String) t.get("eventDate");
                            lbr.setEventDate(eDate != null && !eDate.isEmpty() ? Instant.parse(eDate).atZone(ZoneId.of("UTC")).toLocalDateTime() : null);
                            lbr.setEventName((String) t.get("eventName"));
                            lbr.setIsAddedToMarket(0);
                            lbr.setIsInPlay(0);
                            lbr.setMarketId((String) t.get("marketId"));
                            lbr.setMarketName((String) t.get("marketName"));
                            lbr.setMarketType((String) t.get("marketType"));
                            lbr.setMarket_runner_count((Integer) t.get("market_runner_count"));
                            lbr.setRunnerName1((String) t.get("runnerName1"));
                            lbr.setRunnerName2((String) t.get("runnerName2"));
                            lbr.setSelectionId1((Integer) t.get("selectionId1"));
                            lbr.setSelectionId2((Integer) t.get("selectionId2"));
                            String runnerName3 = (String) t.get("runnerName3");
                            if (runnerName3 != null) {
                                lbr.setRunnerName3((String) t.get("runnerName3"));
                                lbr.setSelectionId3((Integer) t.get("selectionId3"));
                            } else {
                                lbr.setRunnerName3(null);
                                lbr.setSelectionId3(0);
                            }
                            lbr.setMatch_type((String) t.get("match_type"));
                            lbr.setSrno((Integer) t.get("srno"));
                            String sDate = (String) t.get("startDate");
                            lbr.setStartDate(sDate != null && !sDate.isEmpty() ? Instant.parse(sDate).atZone(ZoneId.of("UTC")).toLocalDateTime() : null);
                            try {
                                Date date = iso8601Format.parse(sDate);
                                SimpleDateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy, h:mm:ss a");
                                String formattedDate = targetFormat.format(date);
                                lbr.setStartDateIST(formattedDate);
                                lbr.setMatchTimeIST(formattedDate);
                            } catch (ParseException e) {
                                continue;
                            }

                            matchDetailsModelList.add(lbr);
                        }
                    }

                }

            }

        }

    matchDetailsModelRepository.saveAll(matchDetailsModelList);
        System.out.println(("BetFair_Tennis"));

    }
    @Scheduled(fixedDelay = 180 * 1000)
    public void fetchDataFormDMDForCricket() {

        List<matchDetailsModel> matchDetailsModelList = new ArrayList<>();

        List<Object> dataMap = fetchDataFromApiDMDCricket();

        for (Object entry : dataMap) {

            if (entry instanceof Map) {
                Map<?, ?> entryMap = (Map<?, ?>) entry;
                        matchDetailsModel m = matchDetailsModelRepository.findByEventId((String) entryMap.get("gameId"));
                        if (m != null) {
                            Query query = new Query(Criteria.where("eventId").is((String) entryMap.get("gameId")));
                            Update update = new Update()
                                    .set("OddsSource", "DMD")
                                    .set("Source", "DMD");

                            mongoTemplate.updateFirst(query, update, matchDetailsModel.class);
                        } else {

                            matchDetailsModel lbr = new matchDetailsModel();
                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                            lbr.setEventId((String) entryMap.get("gameId"));
                            String eid = (String) entryMap.get("eid");
                            try {
                                int eventId = Integer.parseInt(eid);
                                lbr.setEventTypeId(eventId);
                            } catch (NumberFormatException ignored) {
                            }

                            String eName = (String) entryMap.get("eventName");
                            String[] parts = eName.split("\\s*/\\s*");
                            if (parts.length >= 2) {
                                String eventName = parts[0].trim();
                                String eventDate = parts[1].trim();

                                lbr.setEventTypeName(eventName);

                                Date dates = null;
                                try {
                                  dates = new SimpleDateFormat("MMM d yyyy h:mma (z)").parse(eventDate);
                                } catch (ParseException ignored) {
                                }
                                LocalDateTime localDateTime = dates.toInstant().atZone(TimeZone.getTimeZone("IST").toZoneId()).toLocalDateTime();
                                String formattedDate = localDateTime.format(TARGET_DATE_FORMATTER);

                                lbr.setEventDate(localDateTime);
                                lbr.setStartDate(localDateTime);
                                lbr.setStartDateIST(formattedDate);
                                lbr.setMatchTimeIST(formattedDate);
                            }
                            lbr.setGameStatus(0);
                            lbr.setSeriesId((String) entryMap.get("gameId"));
                            lbr.setSeriesName((String) entryMap.get("eventName"));
                            lbr.setOddsSource("DMD");
                            lbr.setSource("DMD");
                            lbr.setWinnerTeamId(9999999);
                            lbr.setWinnTeamName((String) entryMap.get(""));
                            lbr.setAddDate(dateTime);
                            lbr.setEventName((String) entryMap.get("eventName"));
                            lbr.setIsAddedToMarket(0);
                            lbr.setIsInPlay((Objects.equals((String) entryMap.get("inPlay"), "True") ? 1 : 0));
                            lbr.setMarketId((String) entryMap.get("marketId"));
                            lbr.setMarket_runner_count(2);
                            lbr.setRunnerName1((String) entryMap.get("gameId"));
                            lbr.setRunnerName2((String) entryMap.get("eventName"));
                            lbr.setSelectionId1(111111111);
                            lbr.setSelectionId2(1111111111);
                                lbr.setRunnerName3("");
                                lbr.setSelectionId3(0);
                            lbr.setMatch_type("B");
                            lbr.setSrno(1);

                            matchDetailsModelList.add(lbr);
                        }
                    }

        }
        matchDetailsModelRepository.saveAll(matchDetailsModelList);
        System.out.println(("DMD_Cricket"));

    }
    @Scheduled(fixedDelay = 180 * 1000)
    public void fetchDataFormDMDForSoccer() {

        List<matchDetailsModel> matchDetailsModelList = new ArrayList<>();

        List<Object> dataMap = fetchDataFromApiDMDSoccer();

        for (Object entry : dataMap) {

            if (entry instanceof Map) {
                Map<?, ?> entryMap = (Map<?, ?>) entry;
                matchDetailsModel m = matchDetailsModelRepository.findByEventId(String.valueOf(((Integer) entryMap.get("gmid"))));
                if (m != null) {
                    Query query = new Query(Criteria.where("eventId").is(String.valueOf((Integer) entryMap.get("gmid"))));
                    Update update = new Update()
                            .set("OddsSource", "DMD")
                            .set("Source", "DMD");

                    mongoTemplate.updateFirst(query, update, matchDetailsModel.class);
                } else {

                    matchDetailsModel lbr = new matchDetailsModel();
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                    lbr.setEventId(String.valueOf(((Integer) entryMap.get("gmid"))));
                    lbr.setEventTypeId((Integer) entryMap.get("etid"));
                    lbr.setEventTypeName((String) entryMap.get("ename"));
                    String eDate = (String) entryMap.get("stime");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
                    inputFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                    Date dates = null;
                    try {
                        dates = inputFormat.parse(eDate);
                    } catch (ParseException ignored) {
                    }
                    LocalDateTime eDateTime = dates.toInstant().atZone(TimeZone.getTimeZone("IST").toZoneId()).toLocalDateTime();

                        lbr.setEventDate(eDateTime);
                        lbr.setStartDate(eDateTime);
                        lbr.setStartDateIST((String) entryMap.get("stime"));
                        lbr.setMatchTimeIST((String) entryMap.get("stime"));

                    lbr.setGameStatus(0);
                    lbr.setSeriesId("");
                    lbr.setSeriesName((String) entryMap.get("cname"));
                    lbr.setOddsSource("DMD");
                    lbr.setSource("DMD");
                    lbr.setWinnerTeamId(9999999);
                    lbr.setWinnTeamName((String) entryMap.get(""));
                    lbr.setAddDate(dateTime);
                    lbr.setEventName((String) entryMap.get("mname"));
                    lbr.setIsAddedToMarket(0);
                    boolean inP = (boolean) entryMap.get("iplay");
                    int isPlay = inP ? 1 : 0;
                    lbr.setIsInPlay(isPlay);
                    lbr.setMarketId(String.valueOf(entryMap.get("mid")));
                    lbr.setMarket_runner_count((Integer) entryMap.get("m"));
                    lbr.setRunnerName1("");
                    lbr.setRunnerName2("");
                    lbr.setSelectionId1(111111111);
                    lbr.setSelectionId2(1111111111);
                    lbr.setRunnerName3("");
                    lbr.setSelectionId3(0);
                    lbr.setMatch_type("B");
                    lbr.setSrno(1);

                    matchDetailsModelList.add(lbr);
                }
            }

        }
        matchDetailsModelRepository.saveAll(matchDetailsModelList);
        System.out.println(("DMD_Soccer"));

    }
    @Scheduled(fixedDelay = 180 * 1000)
    public void fetchDataFormDMDForTennis() {

        List<matchDetailsModel> matchDetailsModelList = new ArrayList<>();

        List<Object> dataMap = fetchDataFromApiDMDTennis();

        for (Object entry : dataMap) {

            if (entry instanceof Map) {
                Map<?, ?> entryMap = (Map<?, ?>) entry;
                matchDetailsModel m = matchDetailsModelRepository.findByEventId(String.valueOf(((Integer) entryMap.get("gmid"))));
                if (m != null) {
                    Query query = new Query(Criteria.where("eventId").is(String.valueOf(((Integer) entryMap.get("gmid")))));
                    Update update = new Update()
                            .set("OddsSource", "DMD")
                            .set("Source", "DMD");

                    mongoTemplate.updateFirst(query, update, matchDetailsModel.class);
                } else {

                    matchDetailsModel lbr = new matchDetailsModel();
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                    lbr.setEventId(String.valueOf(((Integer) entryMap.get("gmid"))));
                    lbr.setEventTypeId((Integer) entryMap.get("etid"));
                    lbr.setEventTypeName((String) entryMap.get("ename"));
                    String eDate = (String) entryMap.get("stime");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
                    inputFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                    Date dates = null;
                    try {
                        dates = inputFormat.parse(eDate);
                    } catch (ParseException ignored) {
                    }
                    LocalDateTime eDateTime = dates.toInstant().atZone(TimeZone.getTimeZone("IST").toZoneId()).toLocalDateTime();

                    lbr.setEventDate(eDateTime);
                    lbr.setStartDate(eDateTime);
                    lbr.setStartDateIST((String) entryMap.get("stime"));
                    lbr.setMatchTimeIST((String) entryMap.get("stime"));

                    lbr.setGameStatus(0);
                    lbr.setSeriesId("");
                    lbr.setSeriesName((String) entryMap.get("cname"));
                    lbr.setOddsSource("DMD");
                    lbr.setSource("DMD");
                    lbr.setWinnerTeamId(9999999);
                    lbr.setWinnTeamName((String) entryMap.get(""));
                    lbr.setAddDate(dateTime);
                    lbr.setEventName((String) entryMap.get("mname"));
                    lbr.setIsAddedToMarket(0);
                    boolean inP = (boolean) entryMap.get("iplay");
                    int isPlay = inP ? 1 : 0;
                    lbr.setIsInPlay(isPlay);
                    lbr.setMarketId(String.valueOf(entryMap.get("mid")));
                    lbr.setMarket_runner_count((Integer) entryMap.get("m"));
                    lbr.setRunnerName1("");
                    lbr.setRunnerName2("");
                    lbr.setSelectionId1(111111111);
                    lbr.setSelectionId2(1111111111);
                    lbr.setRunnerName3("");
                    lbr.setSelectionId3(0);
                    lbr.setMatch_type("B");
                    lbr.setSrno(1);

                    matchDetailsModelList.add(lbr);
                }
            }

        }
        matchDetailsModelRepository.saveAll(matchDetailsModelList);
        System.out.println(("DMD_Tennis"));

    }


    public LinkedHashMap<String, Object> fetchDataFromApiCricket() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiCricket)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromApiTennis() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiTennis)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromApiSoccer() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiSoccer)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Object> fetchDataFromApiDMDCricket() {
        WebClient webClient = WebClient.builder().build();

        try {
            return webClient.get()
                    .uri(apiDMDCricket)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Object> fetchDataFromApiDMDTennis() {
        WebClient webClient = WebClient.builder().build();

        try {
            return webClient.get()
                    .uri(apiDMDTennis)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Object> fetchDataFromApiDMDSoccer() {
                WebClient webClient = WebClient.builder().build();

        try {
            return webClient.get()
                    .uri(apiDMDSoccer)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

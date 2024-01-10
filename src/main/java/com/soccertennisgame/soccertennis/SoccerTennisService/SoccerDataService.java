package com.soccertennisgame.soccertennis.SoccerTennisService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.soccertennisgame.soccertennis.Model.*;
import com.soccertennisgame.soccertennis.Repository.*;
import com.soccertennisgame.soccertennis.SoccerTennisModel.STData;
import com.soccertennisgame.soccertennis.SoccerTennisModel.SoTeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class SoccerDataService {

    @Value("${api.url}")
    private String apiUrl;
    String apiUrl1 = "http://178.79.149.218:4000/listmarketbook/";
    private final ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    UserWalletRepository userWalletRepository;
    @Autowired
    matchDetailsModelRepository matchDetailsModelRepository;

    @Autowired
    UserWalletService userWalletService;

    @Autowired
    BetTransactionService betTransactionService;
    @Autowired
    BetTransactionRepository betTransactionRepository;
    @Autowired
    FancyDetailsRepository fancyDetailsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MatchPlayListRepository matchPlayListRepository;
    @Autowired
    private SoTeDataRepository soTeDataRepository;
    private List<STData> globalList = new ArrayList<>();
    private final RestTemplate restTemplate;

    public SoccerDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Scheduled(fixedRate = 5 * 1000)
    public void fetchDataAndPrint() {
        System.out.println("Alam");
        List<LinkedHashMap<String, Object>> dataList = fetchDataFromUrl(new ParameterizedTypeReference<List<LinkedHashMap<String, Object>>>() {
        });
        List<STData> filteredDataList = new ArrayList<>();
        for (LinkedHashMap<String, Object> data : dataList) {
            String dateStr = ((String) data.get("adddate"));
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try {

                LocalDateTime addDate = LocalDateTime.parse(dateStr, formatter1);
                STData filteredData = new STData();

                if (data.get("EventTypeId").equals(1) || data.get("EventTypeId").equals(2)) {
                    filteredData.setEventId((String) data.get("eventId"));
                    filteredData.setEventTypeId((Integer) data.get("EventTypeId"));
                    filteredData.setGameStatus((Integer) data.get("GameStatus"));
                    filteredData.setMarketId((String) data.get("marketId"));
                    filteredData.setAddDate(addDate);

                    filteredDataList.add(filteredData);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date string: " + e);
                continue;
            }
        }
        synchronized (globalList) {
            globalList.addAll(filteredDataList);
        }

        fetchMarketIdData();
    }

    public List<Object> fetchDataFromAnotherUrl(String marketId) {
        String apiUrl2 = apiUrl1 + marketId;
        ParameterizedTypeReference<List<Object>> responseType = new ParameterizedTypeReference<List<Object>>() {};
        return fetchDataFromUrl2(responseType, apiUrl2);
    }
    public List<SoTeData> fetchMarketIdData() {
        List<SoTeData> stDataList = new ArrayList<>();

        if (!globalList.isEmpty()) {
            for (STData sTData : globalList) {
                String marketId = sTData.getMarketId();
                List<Object> dataForMarketId = fetchDataFromAnotherUrl(marketId);
                List<SoTeData> mappedData = mapToSoTeData(dataForMarketId);
                stDataList.addAll(mappedData);
            }
        } else {
            throw new IllegalArgumentException("globalList is empty");
        }

        return stDataList;
    }

    private List<SoTeData> mapToSoTeData(List<Object> data) {
        List<SoTeData> mappedData = new ArrayList<>();

        for (Object obj : data) {
            if (obj instanceof Map) {

                Map<String, Object> dataMap = (Map<String, Object>) obj;
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

                Optional<SoTeData> optionalSoTeData = Optional.ofNullable(soTeDataRepository.findByMarketId((String) dataMap.get("marketId")));

                if (optionalSoTeData.isPresent()) {
                    SoTeData soData = optionalSoTeData.get();
                    String marketId = soData.getMarketId();

                    if (marketId != null && Objects.equals(marketId, (String) dataMap.get("marketId"))) {

                        Object totalMV = dataMap.get("totalMatched");
                        Double doubleMV = (totalMV instanceof Double) ? (Double) totalMV : ((Integer) totalMV).doubleValue();

                        Object totalAV = dataMap.get("totalAvailable");
                        Double doubleAV = (totalAV instanceof Double) ? (Double) totalAV : ((Integer) totalAV).doubleValue();

                        Query query = new Query(Criteria.where("marketId").is((String) dataMap.get("marketId")));
                        Update update = new Update()
                                .set("marketId", (String) dataMap.get("marketId"))
                                .set("isMarketDataDelayed", (Boolean) dataMap.get("isMarketDataDelayed"))
                                .set("status", (String) dataMap.get("status"))
                                .set("bspReconciled", (Boolean) dataMap.get("bspReconciled"))
                                .set("complete", (Boolean) dataMap.get("complete"))
                                .set("betDelay", (Integer) dataMap.get("betDelay"))
                                .set("inplay", (Boolean) dataMap.get("inplay"))
                                .set("lastMatchTime", (String) dataMap.get("lastMatchTime"))
                                .set("numberOfWinners", (Integer) dataMap.get("numberOfWinners"))
                                .set("numberOfRunners", (Integer) dataMap.get("numberOfRunners"))
                                .set("numberOfActiveRunners", (Integer) dataMap.get("numberOfActiveRunners"))
                                .set("totalMatched", doubleMV)
                                .set("totalAvailable", doubleAV)
                                .set("crossMatching", (Boolean) dataMap.get("crossMatching"))
                                .set("runnersVoidable", (Boolean) dataMap.get("runnersVoidable"))
                                .set("version", (Long) dataMap.get("version"))
                                .set("isMatchResultUpdated", 0)
                                .set("modDate", dateTime);

                        List<SoTeData.Runner> runnersList = new ArrayList<>();
                        List<Map<String, Object>> runnersData = (List<Map<String, Object>>) dataMap.get("runners");
                        for (Map<String, Object> runnerData : runnersData) {
                            SoTeData.Runner runner = new SoTeData.Runner();
                            if ("LOSER".equals((String) runnerData.get("status"))) {
                                runner.setSelectionId2((Integer) runnerData.get("selectionId"));
                                runner.setStatus2((String) runnerData.get("status"));
                            }

                            if ("WINNER".equals((String) runnerData.get("status"))) {
                                runner.setSelectionId1((Integer) runnerData.get("selectionId"));
                                runner.setStatus1((String) runnerData.get("status"));
                            }

                            if ("DRAW".equals((String) runnerData.get("status"))) {
                                runner.setSelectionId3((Integer) runnerData.get("selectionId"));
                                runner.setStatus3((String) runnerData.get("status"));
                            }
                            runnersList.add(runner);
                        }
                        update.set("runners", runnersList);
                        mongoTemplate.updateMulti(query, update, SoTeData.class);

                    }
                } else {

                    SoTeData soTeData = new SoTeData();

                    Object totalMV = dataMap.get("totalMatched");
                    Double doubleMV = (totalMV instanceof Double) ? (Double) totalMV : ((Integer) totalMV).doubleValue();

                    Object totalAV = dataMap.get("totalAvailable");
                    Double doubleAV = (totalAV instanceof Double) ? (Double) totalAV : ((Integer) totalAV).doubleValue();

                    soTeData.setMarketId((String) dataMap.get("marketId"));
                    soTeData.setIsMarketDataDelayed((Boolean) dataMap.get("isMarketDataDelayed"));
                    soTeData.setStatus((String) dataMap.get("status"));
                    soTeData.setBspReconciled((Boolean) dataMap.get("bspReconciled"));
                    soTeData.setComplete((Boolean) dataMap.get("complete"));
                    soTeData.setInplay((Boolean) dataMap.get("inplay"));
                    soTeData.setLastMatchTime((String) dataMap.get("lastMatchTime"));
                    soTeData.setBetDelay((Integer) dataMap.get("betDelay"));
                    soTeData.setNumberOfWinners((Integer) dataMap.get("numberOfWinners"));
                    soTeData.setNumberOfRunners((Integer) dataMap.get("numberOfRunners"));
                    soTeData.setNumberOfActiveRunners((Integer) dataMap.get("numberOfActiveRunners"));
                    soTeData.setTotalMatched(doubleMV);
                    soTeData.setTotalAvailable(doubleAV);
                    soTeData.setCrossMatching((Boolean) dataMap.get("crossMatching"));
                    soTeData.setRunnersVoidable((Boolean) dataMap.get("runnersVoidable"));
                    soTeData.setVersion((Long) dataMap.get("version"));
                    soTeData.setIsMatchResultUpdated(0);
                    soTeData.setModDate(dateTime);

                    List<SoTeData.Runner> runnersList = new ArrayList<>();
                    List<Map<String, Object>> runnersData = (List<Map<String, Object>>) dataMap.get("runners");
                    for (Map<String, Object> runnerData : runnersData) {
                        SoTeData.Runner runner = new SoTeData.Runner();
                        int selectionId = (Integer) runnerData.get("selectionId");

                        if ("LOSER".equals((String) runnerData.get("status"))) {
                            runner.setSelectionId2((Integer) runnerData.get("selectionId"));
                            runner.setStatus2((String) runnerData.get("status"));
                        }

                        if ("WINNER".equals((String) runnerData.get("status"))) {
                            runner.setSelectionId1((Integer) runnerData.get("selectionId"));
                            runner.setStatus1((String) runnerData.get("status"));
                        }

                        if ("DRAW".equals((String) runnerData.get("status"))) {
                            runner.setSelectionId3((Integer) runnerData.get("selectionId"));
                            runner.setStatus3((String) runnerData.get("status"));
                        } else {
                            runner.setSelectionId3(0);
                            runner.setStatus3(null);
                        }
                        runnersList.add(runner);

                    }

                    soTeData.setRunners(runnersList);

                    mappedData.add(soTeData);

                    soTeDataRepository.saveAll(mappedData);
                }
            }

            }
        closedMatch();
        return mappedData;
    }

    public SoTeData closedMatch(){
        List<SoTeData> m = soTeDataRepository.findByStatusNotAndIsMatchResultUpdated("OPEN", 1);
        if(m != null){
                List<SoTeData> arrayList = new ArrayList<>();
            for (SoTeData st:m) {
                SoTeData soTeData = new SoTeData();
                soTeData.setIsMatchResultUpdated(st.getIsMatchResultUpdated());
                soTeData.setStatus(st.getStatus());
                int selectionId = 0;
                String marketId = st.getMarketId();
                List<SoTeData.Runner> arrData = st.getRunners();
                for (SoTeData.Runner sr : arrData) {
                    String status1 = sr.getStatus1();
                    String status2 = sr.getStatus2();
                    String status3 = sr.getStatus3();

                    if ("WINNER".equals(status1)) {
                        selectionId = sr.getSelectionId1();
                        break;
                    } else if ("WINNER".equals(status2)) {
                        selectionId= sr.getSelectionId2();
                        break;
                    } else if ("WINNER".equals(status3)) {
                        selectionId = sr.getSelectionId3();
                        break;
                    }
                }
                matchDetails(selectionId, marketId);
            }
        }
        return null;
    }
    public void matchDetails(int selectionId, String marketId){

        matchDetailsModel m = matchDetailsModelRepository.findByMarketId(marketId);
            FancyDetails fancyDetails = new FancyDetails();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);
        if(m != null){
            String runnerName;
            int sid1 = m.getSelectionId1();
            int sid2 = m.getSelectionId2();
            int sid3 = m.getSelectionId3();
            if(sid1 == selectionId){
                runnerName = m.getRunnerName1();
            } else if (sid2 == selectionId) {
                runnerName = m.getRunnerName2();
            } else if (sid3 == selectionId) {
                runnerName = m.getRunnerName3();
            } else {
                runnerName = "";
            }
         fancyDetails.setWinnerTeamName(runnerName);
         fancyDetails.setMatchId(Integer.parseInt(m.getEventId()));
         fancyDetails.setMarketId(marketId);
         fancyDetails.setWinnerTeamId(selectionId);

         String userName = "rootAdmin@fairplay111.in";
         matchDetailUpdated(marketId, runnerName, selectionId);
         setMatchResults(fancyDetails, userName);
         addMatchDetailUpdated(marketId, runnerName, selectionId);
            Query query = new Query(Criteria.where("marketId").is(marketId));
            Update update = new Update()
                    .set("isMatchResultUpdated", 1)
                    .set("modDate", dateTime);
            mongoTemplate.updateMulti(query, update, SoTeData.class);
        }

    }

    public void matchDetailUpdated(String marketId, String runnerName, int selectionId){
        Query query = new Query(Criteria.where("marketId").is(marketId));
        Update update = new Update()
                .set("WinnTeamName", runnerName)
                .set("WinnerTeamId", selectionId);
        mongoTemplate.updateMulti(query, update, matchDetailsModel.class);
    }
    public void addMatchDetailUpdated(String marketId, String runnerName, int selectionId){
        Query query = new Query(Criteria.where("marketId").is(marketId));
        Update update = new Update()
                .set("GameStatus", 1)
                .set("WinnTeamName", runnerName)
                .set("WinnerTeamId", selectionId);
        mongoTemplate.updateMulti(query, update, MatchPlayListModel.class);
    }

    public <T> List<T> fetchDataFromUrl(ParameterizedTypeReference<List<T>> typeReference) {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024)) // 1 MB limit
                .build();

        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    public <T> List<T> fetchDataFromUrl2(ParameterizedTypeReference<List<T>> typeReference, String apiUrl2) {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        return webClient.get()
                .uri(apiUrl2)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }



    public String setMatchResults(FancyDetails fancyDetails, String userName) {

        List<String> transId1 = Arrays.asList("B", "O");
        String WinnerTeamName = fancyDetails.getWinnerTeamName();
        List<String> betTransId = new ArrayList<>();
        BetTransaction uBet;
        double betResult = 0.0;
        AggregatedResult resultOdds;
        AggregatedResult resultBook;
        User user;
        List<User> uList = new ArrayList<>();

        List<User> adminNUsersList = new ArrayList<>();
        User adminUser;
        List<String> uniqueUserId = new ArrayList<>();
        List<String> uniqueBetUserId = new ArrayList<>();
        List<UniqueUserModel> uniqueUserIdList = new ArrayList<>();
        UniqueUserModel uniqueUserModel;
        // get all Fancy which are belongs to above matchid

        List<FancyResultsProjection> oddsList =
                betTransactionRepository.getOddsBookByMatchId(fancyDetails.getMatchId(), transId1, 0);

        List<UserWallet> wList = new ArrayList<UserWallet>();
        UserWallet w;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

        MatchPlayListModel matchPlayListModel = matchPlayListRepository.findByEventId(String.valueOf(fancyDetails.getMatchId()));
        String GameTypeName = "NA";

        fancyDetails.setMatchName(matchPlayListModel.getEventName());
        if (matchPlayListModel.getEventTypeId() == 4) {
            GameTypeName = "Cricket";
        } else if (matchPlayListModel.getEventTypeId() == 1) {
            GameTypeName = "soccer";
        } else if (matchPlayListModel.getEventTypeId() == 2) {
            GameTypeName = "Tennis";
        } else if (matchPlayListModel.getEventTypeId() == 20) {
            GameTypeName = "Casino";
        } else {
            GameTypeName = "NA";
        }
        if (Objects.equals(String.valueOf(fancyDetails.getFancyResult()), matchPlayListModel.getSelectionId1())) {
            WinnerTeamName = matchPlayListModel.getRunnerName1();
        } else if (Objects.equals(String.valueOf(fancyDetails.getFancyResult()), matchPlayListModel.getSelectionId2())) {
            WinnerTeamName = matchPlayListModel.getRunnerName2();
        } else if (Objects.equals(String.valueOf(fancyDetails.getFancyResult()), matchPlayListModel.getSelectionId3())) {
            WinnerTeamName = matchPlayListModel.getRunnerName3();
        } else {
            WinnerTeamName = "No Result";
        }

        if (fancyDetails.getFancyResult() != 10) { // not No Result
            for (FancyResultsProjection b : oddsList) {
                w = new UserWallet();

                if (
                        ((b.getBetTeamId() != Integer.parseInt(matchPlayListModel.getSelectionId1())) && (b.getBetType() == 1))
                                || ((b.getBetTeamId() != Integer.parseInt(matchPlayListModel.getSelectionId2()) && b.getBetType() == 2))) {
                    return "Match ID is not Matching. Please review !!";
                }

                // Draw for BetTeamId =0  and Results coming as 0
                if (fancyDetails.getFancyResult() == 0) {
                    if (b.getBetType() == 1) {  // winner

                        w.setUserId(b.getUserId());
                        w.setCreatedDate(dateTime);
                        w.setCreatedDateString(timeStamp);
                        w.setCredits(b.getBetProfit());
                        w.setDebit(0);
                        w.setDescriptions(GameTypeName + "/" + fancyDetails.getMatchName() + "/ODDS");
                        w.setMatchName(fancyDetails.getMatchName());
                        w.setRootAdmin(b.getRootAdmin());
                        w.setRoot(b.getRoot());
                        w.setSuperAdmin(b.getSuperAdmin());
                        w.setAdmin(b.getAdmin());
                        w.setSuperSuper(b.getSuperSuper());
                        w.setSuperMaster(b.getSuperMaster());
                        w.setMaster(b.getMaster());
                        w.setTransTypeId(4);
                        w.setAddName(userName);
                        w.setMatchId(fancyDetails.getMatchId());
                        w.setFancyId(fancyDetails.getFancyId());
                        w.setFancyResults(fancyDetails.getFancyResult());
                        w.setWinnerName(WinnerTeamName);
                        w.setSeq(0);
                        w.setCurrentShare(0.0);
                        w.setRemainingShare(0.0);
                        w.setIsActive(0);
                        w.setMarketId("NA");
                        w.setGameTypeName(GameTypeName);
                        w.setActionUserFor("NA");
                        w.setActionForUserId("NA");
                        w.setActionForUserRole("NA");
                        w.setBetForTeam("NA");
                        w.setBetCreatedDate(dateTime);
                        w.setBetCreatedDateString(timeStamp);
                        w.setActualResults("NA");
                        w.setMatchBetType(b.getMatchBetType());
                        w.setAlpha01(0.0);
                        w.setAlpha02(0.0);
                        w.setAlpha03(0.0);
                        w.setMasterShare(b.getBetProfit());
                        w.setSuperMasterShare(b.getBetProfit());
                        w.setSuperSuperShare(b.getBetProfit());
                        w.setAdminShare(b.getBetProfit());
                        w.setSuperAdminShare(b.getBetProfit());
                        w.setRootShare(b.getBetProfit());
                        w.setRootAdminShare(b.getBetProfit());
                        w.setMasterComm(0.0);
                        w.setSuperMasterComm(0.0);
                        w.setSuperSuperComm(0.0);
                        w.setAdminComm(0.0);
                        w.setSuperAdminComm(0.0);
                        w.setRootComm(0.0);
                        w.setRootAdminComm(0.0);
                        w.setMasterSComm(0.0);
                        w.setSuperMasterSComm(0.0);
                        w.setSuperSuperSComm(0.0);
                        w.setAdminSComm(0.0);
                        w.setSuperAdminSComm(0.0);
                        w.setRootSComm(0.0);
                        w.setRootAdminSComm(0.0);
                        w.setMasterShareRemaining(0.0);
                        w.setSuperMasterShareRemaining(0.0);
                        w.setSuperSuperShareRemaining(0.0);
                        w.setAdminShareRemaining(0.0);
                        w.setSuperAdminShareRemaining(0.0);
                        w.setRootShareRemaining(0.0);
                        w.setRootAdminShareRemaining(0.0);
                        w.setUserMap("NA");
                        w.setHomeTeamName(matchPlayListModel.getRunnerName1());
                        w.setAwayTeamName(matchPlayListModel.getRunnerName2());
                        w.setHomeTeamId(Integer.parseInt(matchPlayListModel.getSelectionId1()));
                        w.setAwayTeamId(Integer.parseInt(matchPlayListModel.getSelectionId2()));
                        w.setLeagueName("NA");
                        w.setChar01(b.get_id());
                        w.setChar02("NA");
                        w.setSComm(0.0);
                        w.setCMComm(0.0);
                        w.setMComm(0.0);
                        w.setCSComm(0.0);


                        betResult = b.getBetProfit();
                    } else {
                        w.setUserId(b.getUserId());
                        w.setCreatedDate(dateTime);
                        w.setCreatedDateString(timeStamp);
                        w.setCredits(0);
                        w.setDebit(b.getBetLoss());
                        w.setDescriptions(GameTypeName + "/" + fancyDetails.getMatchName() + "/ODDS");
                        w.setMatchName(fancyDetails.getMatchName());
                        w.setRootAdmin(b.getRootAdmin());
                        w.setRoot(b.getRoot());
                        w.setSuperAdmin(b.getSuperAdmin());
                        w.setAdmin(b.getAdmin());
                        w.setSuperSuper(b.getSuperSuper());
                        w.setSuperMaster(b.getSuperMaster());
                        w.setMaster(b.getMaster());
                        w.setTransTypeId(3);
                        w.setAddName(userName);
                        w.setMatchId(fancyDetails.getMatchId());
                        w.setFancyId(fancyDetails.getFancyId());
                        w.setFancyResults(fancyDetails.getFancyResult());
                        w.setWinnerName(WinnerTeamName);
                        w.setSeq(0);
                        w.setCurrentShare(0.0);
                        w.setRemainingShare(0.0);
                        w.setIsActive(0);
                        w.setMarketId("NA");
                        w.setGameTypeName(GameTypeName);
                        w.setActionUserFor("NA");
                        w.setActionForUserId("NA");
                        w.setActionForUserRole("NA");
                        w.setBetForTeam("NA");
                        w.setBetCreatedDate(dateTime);
                        w.setBetCreatedDateString(timeStamp);
                        w.setActualResults("NA");
                        w.setMatchBetType(b.getMatchBetType());
                        w.setAlpha01(0.0);
                        w.setAlpha02(0.0);
                        w.setAlpha03(0.0);
                        w.setMasterShare(b.getBetLoss());
                        w.setSuperMasterShare(b.getBetLoss());
                        w.setSuperSuperShare(b.getBetLoss());
                        w.setAdminShare(b.getBetLoss());
                        w.setSuperAdminShare(b.getBetLoss());
                        w.setRootShare(b.getBetLoss());
                        w.setRootAdminShare(b.getBetLoss());
                        w.setMasterComm(0.0);
                        w.setSuperMasterComm(0.0);
                        w.setSuperSuperComm(0.0);
                        w.setAdminComm(0.0);
                        w.setSuperAdminComm(0.0);
                        w.setRootComm(0.0);
                        w.setRootAdminComm(0.0);
                        w.setMasterSComm(0.0);
                        w.setSuperMasterSComm(0.0);
                        w.setSuperSuperSComm(0.0);
                        w.setAdminSComm(0.0);
                        w.setSuperAdminSComm(0.0);
                        w.setRootSComm(0.0);
                        w.setRootAdminSComm(0.0);
                        w.setMasterShareRemaining(0.0);
                        w.setSuperMasterShareRemaining(0.0);
                        w.setSuperSuperShareRemaining(0.0);
                        w.setAdminShareRemaining(0.0);
                        w.setSuperAdminShareRemaining(0.0);
                        w.setRootShareRemaining(0.0);
                        w.setRootAdminShareRemaining(0.0);
                        w.setUserMap("NA");
                        w.setHomeTeamName(matchPlayListModel.getRunnerName1());
                        w.setAwayTeamName(matchPlayListModel.getRunnerName2());
                        w.setHomeTeamId(Integer.parseInt(matchPlayListModel.getSelectionId1()));
                        w.setAwayTeamId(Integer.parseInt(matchPlayListModel.getSelectionId2()));
                        w.setLeagueName("NA");
                        w.setChar01(b.get_id());
                        w.setChar02("NA");
                        w.setSComm(0.0);
                        w.setCMComm(0.0);
                        w.setMComm(0.0);
                        w.setCSComm(0.0);
                        betResult = b.getBetLoss();
                    }
                }
                // Draw End here
                // Home Team Winner
                else if (b.getBetType() == 1) {
                    if (fancyDetails.getFancyResult() == b.getBetTeamId()) {  // winner

                        w.setUserId(b.getUserId());
                        w.setCreatedDate(dateTime);
                        w.setCreatedDateString(timeStamp);
                        w.setCredits(b.getBetProfit());
                        w.setDebit(0);
                        w.setDescriptions(GameTypeName + "/" + fancyDetails.getMatchName() + "/ODDS");
                        w.setMatchName(fancyDetails.getMatchName());
                        w.setRootAdmin(b.getRootAdmin());
                        w.setRoot(b.getRoot());
                        w.setSuperAdmin(b.getSuperAdmin());
                        w.setAdmin(b.getAdmin());
                        w.setSuperSuper(b.getSuperSuper());
                        w.setSuperMaster(b.getSuperMaster());
                        w.setMaster(b.getMaster());
                        w.setTransTypeId(4);
                        w.setAddName(userName);
                        w.setMatchId(fancyDetails.getMatchId());
                        w.setFancyId(fancyDetails.getFancyId());
                        w.setFancyResults(fancyDetails.getFancyResult());
                        w.setWinnerName(WinnerTeamName);
                        w.setSeq(0);
                        w.setCurrentShare(0.0);
                        w.setRemainingShare(0.0);
                        w.setIsActive(0);
                        w.setMarketId("NA");
                        w.setGameTypeName(GameTypeName);
                        w.setActionUserFor("NA");
                        w.setActionForUserId("NA");
                        w.setActionForUserRole("NA");
                        w.setBetForTeam("NA");
                        w.setBetCreatedDate(dateTime);
                        w.setBetCreatedDateString(timeStamp);
                        w.setActualResults("NA");
                        w.setMatchBetType(b.getMatchBetType());
                        w.setAlpha01(0.0);
                        w.setAlpha02(0.0);
                        w.setAlpha03(0.0);
                        w.setMasterShare(b.getBetProfit());
                        w.setSuperMasterShare(b.getBetProfit());
                        w.setSuperSuperShare(b.getBetProfit());
                        w.setAdminShare(b.getBetProfit());
                        w.setSuperAdminShare(b.getBetProfit());
                        w.setRootShare(b.getBetProfit());
                        w.setRootAdminShare(b.getBetProfit());
                        w.setMasterComm(0.0);
                        w.setSuperMasterComm(0.0);
                        w.setSuperSuperComm(0.0);
                        w.setAdminComm(0.0);
                        w.setSuperAdminComm(0.0);
                        w.setRootComm(0.0);
                        w.setRootAdminComm(0.0);
                        w.setMasterSComm(0.0);
                        w.setSuperMasterSComm(0.0);
                        w.setSuperSuperSComm(0.0);
                        w.setAdminSComm(0.0);
                        w.setSuperAdminSComm(0.0);
                        w.setRootSComm(0.0);
                        w.setRootAdminSComm(0.0);
                        w.setMasterShareRemaining(0.0);
                        w.setSuperMasterShareRemaining(0.0);
                        w.setSuperSuperShareRemaining(0.0);
                        w.setAdminShareRemaining(0.0);
                        w.setSuperAdminShareRemaining(0.0);
                        w.setRootShareRemaining(0.0);
                        w.setRootAdminShareRemaining(0.0);
                        w.setUserMap("NA");
                        w.setHomeTeamName(matchPlayListModel.getRunnerName1());
                        w.setAwayTeamName(matchPlayListModel.getRunnerName2());
                        w.setHomeTeamId(Integer.parseInt(matchPlayListModel.getSelectionId1()));
                        w.setAwayTeamId(Integer.parseInt(matchPlayListModel.getSelectionId2()));
                        w.setLeagueName("NA");
                        w.setChar01(b.get_id());
                        w.setChar02("NA");
                        w.setSComm(0.0);
                        w.setCMComm(0.0);
                        w.setMComm(0.0);
                        w.setCSComm(0.0);
                        betResult = b.getBetProfit();
                    } else {
                        w.setUserId(b.getUserId());
                        w.setCreatedDate(dateTime);
                        w.setCreatedDateString(timeStamp);
                        w.setCredits(0);
                        w.setDebit(b.getBetLoss());
                        w.setDescriptions(GameTypeName + "/" + fancyDetails.getMatchName() + "/ODDS");
                        w.setMatchName(fancyDetails.getMatchName());
                        w.setRootAdmin(b.getRootAdmin());
                        w.setRoot(b.getRoot());
                        w.setSuperAdmin(b.getSuperAdmin());
                        w.setAdmin(b.getAdmin());
                        w.setSuperSuper(b.getSuperSuper());
                        w.setSuperMaster(b.getSuperMaster());
                        w.setMaster(b.getMaster());
                        w.setTransTypeId(4);
                        w.setAddName(userName);
                        w.setMatchId(fancyDetails.getMatchId());
                        w.setFancyId(fancyDetails.getFancyId());
                        w.setFancyResults(fancyDetails.getFancyResult());
                        w.setWinnerName(WinnerTeamName);
                        w.setSeq(0);
                        w.setCurrentShare(0.0);
                        w.setRemainingShare(0.0);
                        w.setIsActive(0);
                        w.setMarketId("NA");
                        w.setGameTypeName(GameTypeName);
                        w.setActionUserFor("NA");
                        w.setActionForUserId("NA");
                        w.setActionForUserRole("NA");
                        w.setBetForTeam("NA");
                        w.setBetCreatedDate(dateTime);
                        w.setBetCreatedDateString(timeStamp);
                        w.setActualResults("NA");
                        w.setMatchBetType(b.getMatchBetType());
                        w.setAlpha01(0.0);
                        w.setAlpha02(0.0);
                        w.setAlpha03(0.0);
                        w.setMasterShare(b.getBetLoss());
                        w.setSuperMasterShare(b.getBetLoss());
                        w.setSuperSuperShare(b.getBetLoss());
                        w.setAdminShare(b.getBetLoss());
                        w.setSuperAdminShare(b.getBetLoss());
                        w.setRootShare(b.getBetLoss());
                        w.setRootAdminShare(b.getBetLoss());
                        w.setMasterComm(0.0);
                        w.setSuperMasterComm(0.0);
                        w.setSuperSuperComm(0.0);
                        w.setAdminComm(0.0);
                        w.setSuperAdminComm(0.0);
                        w.setRootComm(0.0);
                        w.setRootAdminComm(0.0);
                        w.setMasterSComm(0.0);
                        w.setSuperMasterSComm(0.0);
                        w.setSuperSuperSComm(0.0);
                        w.setAdminSComm(0.0);
                        w.setSuperAdminSComm(0.0);
                        w.setRootSComm(0.0);
                        w.setRootAdminSComm(0.0);
                        w.setMasterShareRemaining(0.0);
                        w.setSuperMasterShareRemaining(0.0);
                        w.setSuperSuperShareRemaining(0.0);
                        w.setAdminShareRemaining(0.0);
                        w.setSuperAdminShareRemaining(0.0);
                        w.setRootShareRemaining(0.0);
                        w.setRootAdminShareRemaining(0.0);
                        w.setUserMap("NA");
                        w.setHomeTeamName(matchPlayListModel.getRunnerName1());
                        w.setAwayTeamName(matchPlayListModel.getRunnerName2());
                        w.setHomeTeamId(Integer.parseInt(matchPlayListModel.getSelectionId1()));
                        w.setAwayTeamId(Integer.parseInt(matchPlayListModel.getSelectionId2()));
                        w.setLeagueName("NA");
                        w.setChar01(b.get_id());
                        w.setChar02("NA");
                        w.setSComm(0.0);
                        w.setCMComm(0.0);
                        w.setMComm(0.0);
                        w.setCSComm(0.0);

                        betResult = b.getBetLoss();
                    }
                } else if (b.getBetType() == 2) {
                    if (fancyDetails.getFancyResult() == b.getBetTeamId()) {  // loss

                        w.setUserId(b.getUserId());
                        w.setCreatedDate(dateTime);
                        w.setCreatedDateString(timeStamp);
                        w.setCredits(0);
                        w.setDebit(b.getBetLoss());
                        w.setDescriptions(GameTypeName + "/" + fancyDetails.getMatchName() + "/ODDS");
                        w.setMatchName(fancyDetails.getMatchName());
                        w.setRootAdmin(b.getRootAdmin());
                        w.setRoot(b.getRoot());
                        w.setSuperAdmin(b.getSuperAdmin());
                        w.setAdmin(b.getAdmin());
                        w.setSuperSuper(b.getSuperSuper());
                        w.setSuperMaster(b.getSuperMaster());
                        w.setMaster(b.getMaster());
                        w.setTransTypeId(4);
                        w.setAddName(userName);
                        w.setMatchId(fancyDetails.getMatchId());
                        w.setFancyId(fancyDetails.getFancyId());
                        w.setFancyResults(fancyDetails.getFancyResult());
                        w.setWinnerName(WinnerTeamName);
                        w.setSeq(0);
                        w.setCurrentShare(0.0);
                        w.setRemainingShare(0.0);
                        w.setIsActive(0);
                        w.setMarketId("NA");
                        w.setGameTypeName(GameTypeName);
                        w.setActionUserFor("NA");
                        w.setActionForUserId("NA");
                        w.setActionForUserRole("NA");
                        w.setBetForTeam("NA");
                        w.setBetCreatedDate(dateTime);
                        w.setBetCreatedDateString(timeStamp);
                        w.setActualResults("NA");
                        w.setMatchBetType(b.getMatchBetType());
                        w.setAlpha01(0.0);
                        w.setAlpha02(0.0);
                        w.setAlpha03(0.0);

                        w.setMasterShare(b.getBetLoss());
                        w.setSuperMasterShare(b.getBetLoss());
                        w.setSuperSuperShare(b.getBetLoss());
                        w.setAdminShare(b.getBetLoss());
                        w.setSuperAdminShare(b.getBetLoss());
                        w.setRootShare(b.getBetLoss());
                        w.setRootAdminShare(b.getBetLoss());

                        w.setMasterComm(0.0);
                        w.setSuperMasterComm(0.0);
                        w.setSuperSuperComm(0.0);
                        w.setAdminComm(0.0);
                        w.setSuperAdminComm(0.0);
                        w.setRootComm(0.0);
                        w.setRootAdminComm(0.0);
                        w.setMasterSComm(0.0);
                        w.setSuperMasterSComm(0.0);
                        w.setSuperSuperSComm(0.0);
                        w.setAdminSComm(0.0);
                        w.setSuperAdminSComm(0.0);
                        w.setRootSComm(0.0);
                        w.setRootAdminSComm(0.0);
                        w.setMasterShareRemaining(0.0);
                        w.setSuperMasterShareRemaining(0.0);
                        w.setSuperSuperShareRemaining(0.0);
                        w.setAdminShareRemaining(0.0);
                        w.setSuperAdminShareRemaining(0.0);
                        w.setRootShareRemaining(0.0);
                        w.setRootAdminShareRemaining(0.0);
                        w.setUserMap("NA");
                        w.setHomeTeamName(matchPlayListModel.getRunnerName1());
                        w.setAwayTeamName(matchPlayListModel.getRunnerName2());
                        w.setHomeTeamId(Integer.parseInt(matchPlayListModel.getSelectionId1()));
                        w.setAwayTeamId(Integer.parseInt(matchPlayListModel.getSelectionId2()));
                        w.setLeagueName("NA");
                        w.setChar01(b.get_id());
                        w.setChar02("NA");
                        w.setSComm(0.0);
                        w.setCMComm(0.0);
                        w.setMComm(0.0);
                        w.setCSComm(0.0);
                        betResult = b.getBetLoss();
                    } else {
                        w.setUserId(b.getUserId());
                        w.setCreatedDate(dateTime);
                        w.setCreatedDateString(timeStamp);
                        w.setCredits(b.getBetProfit());
                        w.setDebit(0);
                        w.setDescriptions(GameTypeName + "/" + fancyDetails.getMatchName() + "/ODDS");
                        w.setMatchName(fancyDetails.getMatchName());
                        w.setRootAdmin(b.getRootAdmin());
                        w.setRoot(b.getRoot());
                        w.setSuperAdmin(b.getSuperAdmin());
                        w.setAdmin(b.getAdmin());
                        w.setSuperSuper(b.getSuperSuper());
                        w.setSuperMaster(b.getSuperMaster());
                        w.setMaster(b.getMaster());
                        w.setTransTypeId(4);
                        w.setAddName(userName);
                        w.setMatchId(fancyDetails.getMatchId());
                        w.setFancyId(fancyDetails.getFancyId());
                        w.setFancyResults(fancyDetails.getFancyResult());
                        w.setWinnerName(WinnerTeamName);
                        w.setSeq(0);
                        w.setCurrentShare(0.0);
                        w.setRemainingShare(0.0);
                        w.setIsActive(0);
                        w.setMarketId("NA");
                        w.setGameTypeName(GameTypeName);
                        w.setActionUserFor("NA");
                        w.setActionForUserId("NA");
                        w.setActionForUserRole("NA");
                        w.setBetForTeam("NA");
                        w.setBetCreatedDate(dateTime);
                        w.setBetCreatedDateString(timeStamp);
                        w.setActualResults("NA");
                        w.setMatchBetType(b.getMatchBetType());
                        w.setAlpha01(0.0);
                        w.setAlpha02(0.0);
                        w.setAlpha03(0.0);
                        w.setMasterShare(b.getBetProfit());
                        w.setSuperMasterShare(b.getBetProfit());
                        w.setSuperSuperShare(b.getBetProfit());
                        w.setAdminShare(b.getBetProfit());
                        w.setSuperAdminShare(b.getBetProfit());
                        w.setRootShare(b.getBetProfit());
                        w.setRootAdminShare(b.getBetProfit());
                        w.setMasterComm(0.0);
                        w.setSuperMasterComm(0.0);
                        w.setSuperSuperComm(0.0);
                        w.setAdminComm(0.0);
                        w.setSuperAdminComm(0.0);
                        w.setRootComm(0.0);
                        w.setRootAdminComm(0.0);
                        w.setMasterSComm(0.0);
                        w.setSuperMasterSComm(0.0);
                        w.setSuperSuperSComm(0.0);
                        w.setAdminSComm(0.0);
                        w.setSuperAdminSComm(0.0);
                        w.setRootSComm(0.0);
                        w.setRootAdminSComm(0.0);
                        w.setMasterShareRemaining(0.0);
                        w.setSuperMasterShareRemaining(0.0);
                        w.setSuperSuperShareRemaining(0.0);
                        w.setAdminShareRemaining(0.0);
                        w.setSuperAdminShareRemaining(0.0);
                        w.setRootShareRemaining(0.0);
                        w.setRootAdminShareRemaining(0.0);
                        w.setUserMap("NA");
                        w.setHomeTeamName(matchPlayListModel.getRunnerName1());
                        w.setAwayTeamName(matchPlayListModel.getRunnerName2());
                        w.setHomeTeamId(Integer.parseInt(matchPlayListModel.getSelectionId1()));
                        w.setAwayTeamId(Integer.parseInt(matchPlayListModel.getSelectionId2()));
                        w.setLeagueName("NA");
                        w.setChar01(b.get_id());
                        w.setChar02("NA");
                        w.setSComm(0.0);
                        w.setCMComm(0.0);
                        w.setMComm(0.0);
                        w.setCSComm(0.0);

                        betResult = b.getBetProfit();
                    }
                }

                betTransId.add(b.get_id());

                uBet = new BetTransaction();
                uBet.setId(b.get_id());
                // update bet table for bet status and bet result.
                Query query = new Query(Criteria.where("matchId").is(fancyDetails.getMatchId()).and("_id").in(b.get_id()));
                Update update = new Update()
                        .set("betResult", betResult)
                        .set("winnerName", WinnerTeamName)
                        .set("betStatus", 1);

                mongoTemplate.updateFirst(query, update, BetTransaction.class);


                wList.add(w);

                if (!uniqueBetUserId.contains(w.getUserId())) {
                    uniqueBetUserId.add(w.getUserId());
                }

            }
            //save transaction to Wallet Node
            userWalletRepository.saveAll(wList);

            for (String u : uniqueBetUserId) {

                resultOdds = betTransactionService.getAllLoad(u, 40, "O", 0);
                resultBook = betTransactionService.getAllLoad(u, 40, "B", 0);
                double minValueBook = 0.0;
                if (resultBook != null) {
                    minValueBook = Math.min(resultBook.getHomeTeamPosition(), Math.min(resultBook.getAwayTeamPosition(), resultBook.getDrawTeamPosition()));

                }

                double minValueOdds = 0.0;
                if (resultOdds != null) {
                    minValueOdds = Math.min(resultOdds.getHomeTeamPosition(), Math.min(resultOdds.getAwayTeamPosition(), resultOdds.getDrawTeamPosition()));
                }
                user = new User();
                user = userRepository.findByEmail(u);
                double winning = userWalletService.getUserWinning(user.getEmail());

                user.setBook_Exposer(minValueBook);
                user.setOdds_Exposer(minValueOdds);
                user.setProfit(winning);
                user.setBalance(userWalletService.getBalanceFromWallet(u));

                uList.add(user);

                // pull uniquer non-user Id
                if (!uniqueUserId.contains(user.getMaster())) {

                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getMaster());
                    uniqueUserModel.setRoleId(35);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getMaster());
                }
                if (!uniqueUserId.contains(user.getSuperMaster())) {
                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getSuperMaster());
                    uniqueUserModel.setRoleId(30);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getSuperMaster());
                }
                if (!uniqueUserId.contains(user.getSuperSuper())) {
                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getSuperSuper());
                    uniqueUserModel.setRoleId(25);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getSuperSuper());
                }
                if (!uniqueUserId.contains(user.getAdmin())) {
                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getAdmin());
                    uniqueUserModel.setRoleId(20);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getAdmin());
                }
                if (!uniqueUserId.contains(user.getSuperAdmin())) {
                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getSuperAdmin());
                    uniqueUserModel.setRoleId(15);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getSuperAdmin());
                }
                if (!uniqueUserId.contains(user.getRoot())) {
                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getRoot());
                    uniqueUserModel.setRoleId(10);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getRoot());
                }
                if (!uniqueUserId.contains(user.getRootAdmin())) {
                    uniqueUserModel = new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getRootAdmin());
                    uniqueUserModel.setRoleId(5);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getRootAdmin());
                }
            }


            userRepository.saveAll(uList);

            for (UniqueUserModel uu : uniqueUserIdList) {
                double winning = userWalletService.getUserWinningByRole(uu.getUserId(), uu.getRoleId());
                adminUser = userRepository.findByEmail(uu.getUserId());
                adminUser.setProfit(winning);
                adminNUsersList.add(adminUser);
            }
            userRepository.saveAll(adminNUsersList);


            fancyDetails.setAddDate(dateTime);
            fancyDetails.setAddDateString(timeStamp);
            fancyDetails.setMarketType("O");
            fancyDetailsRepository.save(fancyDetails);

            matchPlayListModel.setGameStatus(1);


            matchPlayListRepository.deleteByEventId(Integer.toString(fancyDetails.getMatchId()));
            matchPlayListRepository.save(matchPlayListModel);

            return "Update Successfully !!";
        } else { // this is for No Result
            List<String> matchBetType = Arrays.asList("O", "B");
            // update bet table for bet status and bet result.
            Query query = new Query(Criteria.where("matchId").is(fancyDetails.getMatchId()).and("matchBetType").in(matchBetType));
            Update update = new Update()
                    .set("betResult", betResult)
                    .set("winnerName", WinnerTeamName)
                    .set("betStatus", 10);

            mongoTemplate.updateMulti(query, update, BetTransaction.class);
            for (FancyResultsProjection b : oddsList) {
                resultOdds = betTransactionService.getAllLoad(b.getUserId(), 40, "O", 0);
                resultBook = betTransactionService.getAllLoad(b.getUserId(), 40, "B", 0);
                double minValueBook = 0.0;
                if (resultBook != null) {
                    minValueBook = Math.min(resultBook.getHomeTeamPosition(), Math.min(resultBook.getAwayTeamPosition(), resultBook.getDrawTeamPosition()));

                }

                double minValueOdds = 0.0;
                if (resultOdds != null) {
                    minValueOdds = Math.min(resultOdds.getHomeTeamPosition(), Math.min(resultOdds.getAwayTeamPosition(), resultOdds.getDrawTeamPosition()));
                }
                user = new User();
                user = userRepository.findByEmail(b.getUserId());

                user.setBook_Exposer(minValueBook);
                user.setOdds_Exposer(minValueOdds);

                uList.add(user);

            }
            userRepository.saveAll(uList);

            fancyDetails.setAddDate(dateTime);
            fancyDetails.setAddDateString(timeStamp);
            fancyDetails.setMarketType("O");
            fancyDetailsRepository.save(fancyDetails);

            Query query2 = new Query(Criteria.where("eventId").is(String.valueOf(fancyDetails.getMatchId())));
            Update update2 = new Update()
                    .set("GameStatus", 1);
            mongoTemplate.updateMulti(query2, update2, MatchPlayListModel.class);

            return "No Results Updated Successfully !!";
        }
    }


}
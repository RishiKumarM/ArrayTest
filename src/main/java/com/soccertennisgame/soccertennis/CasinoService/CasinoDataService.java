package com.soccertennisgame.soccertennis.CasinoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.soccertennisgame.soccertennis.Model.*;
import com.soccertennisgame.soccertennis.PostgreSQLRepository.BetTransactionPostRepository;
import com.soccertennisgame.soccertennis.PostgreSQLRepository.UserWalletPostRepository;
import com.soccertennisgame.soccertennis.RedisRepository.lucky7bRepository;
import com.soccertennisgame.soccertennis.Repository.*;
import com.soccertennisgame.soccertennis.SqlRepository.EX_T1_Table_Live_TLRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CasinoDataService {

    @Value("${api.lucky7bData}")
    private String apiUrl12;
    @Value("${api.lucky7bResult}")
    private String apiUrl13;
    @Value("${api.teenpatiData}")
    private String apiUrl14;
    @Value("${api.teenpatiResult}")
    private String apiUrl15;
    @Value("${api.teen}")
    private String apiUrl16;
    @Value("${api.teenResult}")
    private String apiUrl17;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private EX_T1_Table_Live_TLRepository exT1TableLiveTlRepository;

    @Autowired
    public CasinoDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Autowired
    lucky7bRepository lucky7bRepository;

    @Autowired
    Luck7bResultMongoRepository luck7bResultMongoRepository;

    @Autowired
    BetTransactionRepository betTransactionRepository;

    @Autowired
    Lucky7bModelMongoRepository lucky7bModelMongoRepository;
    @Autowired
    UserWalletRepository userWalletRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserWalletPostRepository userWalletPostRepository;
    @Autowired
    private BetTransactionPostRepository betTransactionPostRepository;



//    @Scheduled(fixedRate =  1000)
    public void fetchDataFromAnotherUrl() throws JsonProcessingException {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

        List<lucky7bModel> lucky7bModelsList=new ArrayList<>();
        lucky7bModel m;
        Lucky7bModelMongo m1;
        LinkedHashMap<String, Object> dataMap = fetchDataFromUrl();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (!Objects.equals(key, "success")) {


            LinkedHashMap<String, Object> innerMap = (LinkedHashMap<String, Object>) entry.getValue();


            for (Map.Entry<String, Object> innerEntry : innerMap.entrySet()) {


                String innerKey = innerEntry.getKey();
                ArrayList<?> innerValue = (ArrayList<?>) innerEntry.getValue();


                // Check if the inner value is an ArrayList
                if (innerValue instanceof ArrayList) {
                    // Explicit casting to ArrayList
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerValue;

                    // Loop over the ArrayList and process each element
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        m=new lucky7bModel();
                        m1=new Lucky7bModelMongo();
                        m.setNodeType(innerKey);
                        for (Map.Entry<String, Object> d : t.entrySet()) {
                            String mid_key = d.getKey();
                            String mid_vale = d.getValue().toString();
                            String mid_vale1 = d.getValue().toString();

                            if (Objects.equals(innerKey, "t2")) {
                                if (Objects.equals(mid_key, "mid")) {
                                    m.setMid(mid_vale);
                                }
                                if (Objects.equals(mid_key, "nat")) {
                                    m.setNat(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "sid")) {
                                    m.setSid(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "rate")) {
                                    m.setRate(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "gstatus")) {
                                    m.setGstatus(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "min")) {
                                    m.setMin((Integer) d.getValue());
                                }
                                if (Objects.equals(mid_key, "max")) {
                                    m.setMax((Integer) d.getValue());
                                }
                            }
                                if (Objects.equals(innerKey, "t1")) {
                                    if (Objects.equals(mid_key, "mid")) {
                                        m.setMid(mid_vale1);
                                        m1.setMid(mid_vale1);
                                    }

                                    if (Objects.equals(mid_key, "autotime")) {
                                        m.setAutotime(mid_vale1);
                                        m1.setAutotime(mid_vale1);
                                    }

                                    if (Objects.equals(mid_key, "gtype")) {
                                        m.setGtype(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "C1")) {
                                        m.setC1(mid_vale1);
                                        m1.setC1(mid_vale1);
                                    }
                                    m1.setAdddateSTR(timeStamp);
                                    m1.setAdddate(dateTime);
                                    Lucky7bModelMongo mm=lucky7bModelMongoRepository.findByMid(m1.getMid());
                                    if(mm!=null){
                                        mm.setC1(m1.getC1());
                                        mm.setAutotime(m1.getAutotime());
                                        lucky7bModelMongoRepository.save(mm);
                                    }else{
                                        lucky7bModelMongoRepository.save(m1);
                                    }

                                }

                        }
                        lucky7bModelsList.add(m);

                    }

                }

            }

        }

        }
        final ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(lucky7bModelsList);
        System.out.println(("Lucky7B_data"));
        redisTemplate.opsForValue().set("lucky7bData",json);
    }
//    @Scheduled(fixedRate =  1000)
    public void fetchDataFromAnotherUrl5() throws JsonProcessingException {

        List<Teen> teenList=new ArrayList<>();
        Teen m;
        LinkedHashMap<String, Object> dataMap = fetchDataFromUrl5();
        if(dataMap != null){
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (!Objects.equals(key, "success")) {

                LinkedHashMap<String, Object> innerMap = (LinkedHashMap<String, Object>) entry.getValue();

                for (Map.Entry<String, Object> innerEntry : innerMap.entrySet()) {


                    String innerKey = innerEntry.getKey();
                    ArrayList<?> innerValue = (ArrayList<?>) innerEntry.getValue();


                    // Check if the inner value is an ArrayList
                    if (innerValue instanceof ArrayList) {
                        // Explicit casting to ArrayList
                        ArrayList<?> innerArrayList = (ArrayList<?>) innerValue;

                        // Loop over the ArrayList and process each element
                        for (int i = 0; i < innerArrayList.size(); i++) {
                            LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                            m = new Teen();
                            for (Map.Entry<String, Object> d : t.entrySet()) {
                                String mid_key = d.getKey();
                                String mid_vale = d.getValue().toString();
                                String mid_vale1 = d.getValue().toString();

                                if (Objects.equals(innerKey, "bf")) {
                                    if (Objects.equals(mid_key, "marketId")) {
                                        m.setMarketId(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "nation")) {
                                        m.setNation(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "Srno")) {
                                        m.setSrno(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "b1")) {
                                        m.setB1(Integer.parseInt(mid_vale1));
                                    }
                                    if (Objects.equals(mid_key, "bs1")) {
                                        m.setBs1(Integer.parseInt(mid_vale1));
                                    }
                                    if (Objects.equals(mid_key, "l1")) {
                                        m.setL1(Integer.parseInt(mid_vale1));
                                    }
                                    if (Objects.equals(mid_key, "ls1")) {
                                        m.setLs1(Integer.parseInt(mid_vale1));
                                    }
                                    if (Objects.equals(mid_key, "remark")) {
                                        m.setRemark(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "UpdateTime")) {
                                        m.setUpdateTime(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "lasttime")) {
                                        m.setLasttime(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "sectionId")) {
                                        m.setSectionId(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "gstatus")) {
                                        m.setGstatus(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "gameType")) {
                                        m.setGameType(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "min")) {
                                        m.setMin(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "max")) {
                                        m.setMax(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "C1")) {
                                        m.setC1(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "C2")) {
                                        m.setC2(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "C3")) {
                                        m.setC3(mid_vale1);
                                    }
                                }

                            }
                            teenList.add(m);
                        }

                    }

                }
            }
        }

        }
        final ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(teenList);
        System.out.println(("TeenData"));
        redisTemplate.opsForValue().set("TeenData",json);

    }
//   @Scheduled(fixedRate =  1000)
    public void fetchDataFromAnotherUrl3() throws JsonProcessingException {

        List<TeenpatiModel> teenpatiModelList=new ArrayList<>();
        TeenpatiModel m;
        LinkedHashMap<String, Object> dataMap = fetchDataFromUrl3();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (!Objects.equals(key, "success")) {

            LinkedHashMap<String, Object> innerMap = (LinkedHashMap<String, Object>) entry.getValue();

            for (Map.Entry<String, Object> innerEntry : innerMap.entrySet()) {


                String innerKey = innerEntry.getKey();
                ArrayList<?> innerValue = (ArrayList<?>) innerEntry.getValue();


                // Check if the inner value is an ArrayList
                if (innerValue instanceof ArrayList) {
                    // Explicit casting to ArrayList
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerValue;

                    // Loop over the ArrayList and process each element
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        m=new TeenpatiModel();
                        m.setNodeType(innerKey);
                        for (Map.Entry<String, Object> d : t.entrySet()) {
                            String mid_key = d.getKey();
                            String mid_vale = d.getValue().toString();
                            String mid_vale1 = d.getValue().toString();

                            if (Objects.equals(innerKey, "t2")) {
                                if (Objects.equals(mid_key, "mid")) {
                                    m.setMid(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "nation")) {
                                    m.setNation(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "sid")) {
                                    m.setSid(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "rate")) {
                                    m.setRate(mid_vale1);
                                }
                                if (Objects.equals(mid_key, "gstatus")) {
                                    m.setGstatus(mid_vale1);
                                }

                            }
                                if (Objects.equals(innerKey, "t1")) {
                                    if (Objects.equals(mid_key, "mid")) {
                                        m.setMid(mid_vale1);
                                    }

                                    if (Objects.equals(mid_key, "autotime")) {
                                        m.setAutotime(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "remark")) {
                                        m.setRemark(mid_vale1);
                                    }
                                    if (Objects.equals(mid_key, "gtype")) {
                                        m.setGtype(mid_vale1);
                                    }if (Objects.equals(mid_key, "min")) {
                                        m.setMin(Integer.parseInt(mid_vale1));
                                    }if (Objects.equals(mid_key, "max")) {
                                        m.setMax(Integer.parseInt(mid_vale1));
                                    }
                                    if (Objects.equals(mid_key, "C1")) {
                                        m.setC1(mid_vale1);
                                    }if (Objects.equals(mid_key, "C2")) {
                                        m.setC2(mid_vale1);
                                    }if (Objects.equals(mid_key, "C3")) {
                                        m.setC3(mid_vale1);
                                    }if (Objects.equals(mid_key, "C4")) {
                                        m.setC4(mid_vale1);
                                    }if (Objects.equals(mid_key, "C5")) {
                                        m.setC5(mid_vale1);
                                    }if (Objects.equals(mid_key, "C6")) {
                                        m.setC6(mid_vale1);
                                    }


                                }

                        }
                        teenpatiModelList.add(m);
                    }

                }

            }

        }

        }
        final ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(teenpatiModelList);
        System.out.println(("Teen20_Data"));
        redisTemplate.opsForValue().set("Teen20",json);
    }



//    @Scheduled(fixedRate =  10000)
    public void fetchDataFromAnotherUrl2() throws JsonProcessingException {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

        List<Luck7bResult> lucky7bResultList =new ArrayList<>();;

        LinkedHashMap<String, Object> dataMap = fetchDataFromUrl2();
        Luck7bResult lbr;
        Luck7bResult lbr1;
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (Objects.equals(key, "data")) {

                Object innerMap1 = entry.getValue();
                if (innerMap1 instanceof ArrayList) {
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerMap1;
                    for (int i = 0; i < innerArrayList.size(); i++) {

                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                       lbr=luck7bResultMongoRepository.findByMid((String) t.get("mid"));

                       if(lbr==null){

                           lbr1 = new Luck7bResult();
                           lbr1.setMid((String) t.get("mid"));
                           lbr1.setResult((String) t.get("result"));
                           lbr1.setAdddate(dateTime);
                           lbr1.setAdddateSTR(timeStamp);

                           String res=(String) t.get("result");

                           if(Objects.equals(res, "1")){
                               lbr1.setWinnerName("Low Card");
                           }

                           else if(Objects.equals(res, "2")){
                               lbr1.setWinnerName("High Card");
                           }
                           else{
                               lbr1.setWinnerName("Tie");
                           }

                           lucky7bResultList.add(lbr1);
                       }





                    }

                }

            }

        }
        //final ObjectMapper objectMapper = new ObjectMapper();
       // String json = objectMapper.writeValueAsString(lucky7bResultList);
        System.out.println(("Lucky7B_Result"));
        luck7bResultMongoRepository.saveAll(lucky7bResultList);
        //redisTemplate.opsForValue().set("luck7bResult",json);

    }
  //  @Scheduled(fixedRate =  1000)
    public void fetchDataFromAnotherUrl4() throws JsonProcessingException {

        List<TeenpatiResult> teenPatiResultList =new ArrayList<>();;

        LinkedHashMap<String, Object> dataMap = fetchDataFromUrl4();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (Objects.equals(key, "data")) {

                Object innerMap1 = entry.getValue();
                if (innerMap1 instanceof ArrayList) {
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerMap1;
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        TeenpatiResult lbr = new TeenpatiResult();
                       lbr.setMid((String) t.get("mid"));
                       lbr.setResult((String) t.get("result"));

                        teenPatiResultList.add(lbr);
                    }

                }

            }

        }

        final ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(teenPatiResultList);
        System.out.println(("Teen20_Result"));
        redisTemplate.opsForValue().set("Teen20Result",json);

    }
//    @Scheduled(fixedRate =  1000)
    public void fetchDataFromAnotherUrl6() throws JsonProcessingException {

        List<TeenResult> teenResultList =new ArrayList<>();;

        LinkedHashMap<String, Object> dataMap = fetchDataFromUrl6();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();

            if (Objects.equals(key, "data")) {

                Object innerMap1 = entry.getValue();
                if (innerMap1 instanceof ArrayList) {
                    ArrayList<?> innerArrayList = (ArrayList<?>) innerMap1;
                    for (int i = 0; i < innerArrayList.size(); i++) {
                        LinkedHashMap<String, Object> t = (LinkedHashMap<String, Object>) innerArrayList.get(i);
                        TeenResult lbr = new TeenResult();
                       lbr.setMid((String) t.get("mid"));
                       lbr.setResult((String) t.get("result"));
                        teenResultList.add(lbr);
                    }

                }

            }

        }

        final ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(teenResultList);
        System.out.println(("Teen_Result"));
        redisTemplate.opsForValue().set("TeenResult",json);

    }

    public String getLucky7bData(String key) {
        Object result = redisTemplate.opsForValue().get(key);
        String jsonString;
        if (result != null) {
           return jsonString = (String) result;
        } else {
            return "No data found in Redis";
        }
    }
    public String getResultData(String key) {
        Object result = redisTemplate.opsForValue().get(key);
        String jsonString;
        if (result != null) {
           return jsonString = (String) result;
        } else {
            return "No data found in Redis";
        }
    }





    public LinkedHashMap<String, Object> fetchDataFromUrl() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiUrl12)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromUrl2() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiUrl13)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromUrl3() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiUrl14)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromUrl4() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiUrl15)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromUrl5() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiUrl16)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public LinkedHashMap<String, Object> fetchDataFromUrl6() {
        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();

        try {
            return webClient.get()
                    .uri(apiUrl17)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //@Scheduled(fixedRate =  20000)
    public String setCasinoResults(){
        boolean isResultExist=false;
        String winnerTeamName="";
        List<String> betTransId=new ArrayList<>();
        BetTransaction uBet;
        double betResult=0.0;
        AggregatedResult resultOdds;
        AggregatedResult resultBook;
        User user;
        List<User> uList=new ArrayList<>();

        List<User> adminNUsersList=new ArrayList<>();
        User adminUser;
        List<String> uniqueUserId=new ArrayList<>();
        List<String> uniqueBetUserId=new ArrayList<>();
        List<String> uniqueMid=new ArrayList<>();
        List<UniqueUserModel> uniqueUserIdList=new ArrayList<>();
        UniqueUserModel uniqueUserModel;
        // get all Fancy which are belongs to above matchid

        List<BetTransaction> oddsList=betTransactionRepository.findByMatchIdAndBetStatus(5100,0);

        List<UserWallet>  wList=new ArrayList<UserWallet>();
        UserWallet w;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

        //MatchPlayListModel matchPlayListModel=matchPlayListRepository.findByEventId(String.valueOf(fancyDetails.getMatchId()));
        String GameTypeName="NA";

//        if(matchPlayListModel.getEventTypeId()==4){
//            GameTypeName="Cricket";
//        }else if(matchPlayListModel.getEventTypeId()==1){
//            GameTypeName="soccer";
//        }else if(matchPlayListModel.getEventTypeId()==2){
//            GameTypeName="Tennis";
//        }else if(matchPlayListModel.getEventTypeId()==20){
//            GameTypeName="Casino";
//        }else{
//            GameTypeName="NA";
//        }

        GameTypeName="Casino";


        Luck7bResult luck7bResult;
        Lucky7bModelMongo lucky7bModelMongoResultCard;

        for (BetTransaction b : oddsList) {
            // get Results
            luck7bResult = luck7bResultMongoRepository.findByMid(b.getMarketId());
            lucky7bModelMongoResultCard = lucky7bModelMongoRepository.findByMid(b.getMarketId());

            w = new UserWallet();
        if(luck7bResult!=null){
            isResultExist=true;
            if (
                    (Objects.equals(luck7bResult.getResult(), "1") &&
                            (
                                    Objects.equals(b.getCasinoBetType(), "LC")
                                            && (
                                            Objects.equals(luck7bResult.getResult(), "1") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "2")
                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "3") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "4") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "5")
                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "6") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "A")
                                    )
                            )
                            ||
                            (
                                    Objects.equals(b.getCasinoBetType(), "C1") && (Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "1")
                                            || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "A"))
                            )

                            || (Objects.equals(b.getCasinoBetType(), "C2") && Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "2"))
                            || (Objects.equals(b.getCasinoBetType(), "C3") && Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "3"))
                            || (Objects.equals(b.getCasinoBetType(), "C4") && Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "4"))
                            || (Objects.equals(b.getCasinoBetType(), "C5") && Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "5"))
                            || (Objects.equals(b.getCasinoBetType(), "C6") && Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "6"))
                            || (Objects.equals(b.getCasinoBetType(), "EC") && Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "10"))
                            ||
                            (
                                    Objects.equals(b.getCasinoBetType(), "EC")
                                            && (
                                            Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "2") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "4")
                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "6") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "8") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "Q")
                                    )
                            )
                            ||
                            (
                                    Objects.equals(b.getCasinoBetType(), "OC")
                                            && (
                                            Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "A") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "J")
                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "K") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "1") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "3")
                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "5") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "7") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "9")
                                    )
                            )
                            ||
                            (
                                    Objects.equals(b.getCasinoBetType(), "RC")
                                            && (
                                            Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(lucky7bModelMongoResultCard.getC1().length() - 2), "SS") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(lucky7bModelMongoResultCard.getC1().length() - 2), "DD")
                                    )
                            )
                            ||
                            (
                                    Objects.equals(b.getCasinoBetType(), "BC")
                                            && (
                                            Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(lucky7bModelMongoResultCard.getC1().length() - 2), "HH") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(lucky7bModelMongoResultCard.getC1().length() - 2), "CC")
                                    )
                            )
                    )
                            ||
                            (
                                    Objects.equals(luck7bResult.getResult(), "2")
                                            &&
                                            ((

                                                    Objects.equals(b.getCasinoBetType(), "HC")
                                                            && (
                                                            Objects.equals(luck7bResult.getResult(), "1") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "8")
                                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "9") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "J") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "K")
                                                                    || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "Q") || Objects.equals((lucky7bModelMongoResultCard.getC1()).substring(0, 1), "10")
                                                    )

                                            )
                                                    || (Objects.equals(b.getCasinoBetType(), "C8") && Objects.equals(luck7bResult.getResult(), "8"))
                                                    || (Objects.equals(b.getCasinoBetType(), "C9") && Objects.equals(luck7bResult.getResult(), "9"))
                                                    || (Objects.equals(b.getCasinoBetType(), "C10") && Objects.equals(luck7bResult.getResult(), "10"))
                                                    || (Objects.equals(b.getCasinoBetType(), "CK") && Objects.equals(luck7bResult.getResult(), "K"))
                                                    || (Objects.equals(b.getCasinoBetType(), "CQ") && Objects.equals(luck7bResult.getResult(), "Q"))
                                                    || (Objects.equals(b.getCasinoBetType(), "CJ") && Objects.equals(luck7bResult.getResult(), "J"))

                                            )
                            )
            )
            {
                w.setUserId(b.getUserId());
                w.setCreatedDate(dateTime);
                w.setCreatedDateString(timeStamp);
                w.setCredits(b.getBetProfit());
                w.setDebit(0);
                w.setDescriptions(GameTypeName + "/" + b.getMatchName()+ "/" + b.getBetForTeam()+"/W. "+luck7bResult.getWinnerName());
                w.setMatchName(b.getMatchName());
                w.setRootAdmin(b.getRootAdmin());
                w.setRoot(b.getRoot());
                w.setSuperAdmin(b.getSuperAdmin());
                w.setAdmin(b.getAdmin());
                w.setSuperSuper(b.getSuperSuper());
                w.setSuperMaster(b.getSuperMaster());
                w.setMaster(b.getMaster());
                w.setTransTypeId(20);
                w.setAddName("auto");
                w.setMatchId(b.getMatchId());
                w.setFancyId(b.getFancyId());
                w.setFancyResults(0);
                w.setWinnerName(luck7bResult.getWinnerName());
                w.setSeq(0);
                w.setCurrentShare(0.0);
                w.setRemainingShare(0.0);
                w.setIsActive(0);
                w.setMarketId(b.getMarketId());
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
                w.setHomeTeamName("NA");
                w.setAwayTeamName("NA");
                w.setHomeTeamId(5100);
                w.setAwayTeamId(5100);
                w.setLeagueName("NA");
                w.setChar01(b.getId());
                w.setChar02("NA");
                w.setSComm(0.0);
                w.setCMComm(0.0);
                w.setMComm(0.0);
                w.setCSComm(0.0);


                betResult = b.getBetProfit();
            } else if ( // 50% loss
                    Objects.equals(luck7bResult.getResult(), "0")
                            && ((Objects.equals(b.getCasinoBetType(), "HC") || (Objects.equals(b.getCasinoBetType(), "LC"))))
            )
            {
                w.setUserId(b.getUserId());
                w.setCreatedDate(dateTime);
                w.setCreatedDateString(timeStamp);
                w.setCredits(0);
                w.setDebit(b.getBetLoss() * 0.50);
                w.setDescriptions(GameTypeName + "/" + b.getMatchName()+ "/" + b.getBetForTeam()+"/W. "+luck7bResult.getWinnerName());
                w.setMatchName(b.getMatchName());
                w.setRootAdmin(b.getRootAdmin());
                w.setRoot(b.getRoot());
                w.setSuperAdmin(b.getSuperAdmin());
                w.setAdmin(b.getAdmin());
                w.setSuperSuper(b.getSuperSuper());
                w.setSuperMaster(b.getSuperMaster());
                w.setMaster(b.getMaster());
                w.setTransTypeId(20);
                w.setAddName("auto");
                w.setMatchId(b.getMatchId());
                w.setFancyId(b.getFancyId());
                w.setFancyResults(0);
                w.setWinnerName(luck7bResult.getWinnerName());
                w.setSeq(0);
                w.setCurrentShare(0.0);
                w.setRemainingShare(0.0);
                w.setIsActive(0);
                w.setMarketId(b.getMarketId());
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
                w.setHomeTeamName("NA");
                w.setAwayTeamName("NA");
                w.setHomeTeamId(5100);
                w.setAwayTeamId(5100);
                w.setLeagueName("NA");
                w.setChar01(b.getId());
                w.setChar02("NA");
                w.setSComm(0.0);
                w.setCMComm(0.0);
                w.setMComm(0.0);
                w.setCSComm(0.0);


                betResult = b.getBetLoss() * 0.50;
            }
            else
            {
                w.setUserId(b.getUserId());
                w.setCreatedDate(dateTime);
                w.setCreatedDateString(timeStamp);
                w.setCredits(0);
                w.setDebit(b.getBetLoss());
                w.setDescriptions(GameTypeName + "/" + b.getMatchName()+ "/" + b.getBetForTeam()+"/W. "+luck7bResult.getWinnerName());
                w.setMatchName(b.getMatchName());
                w.setRootAdmin(b.getRootAdmin());
                w.setRoot(b.getRoot());
                w.setSuperAdmin(b.getSuperAdmin());
                w.setAdmin(b.getAdmin());
                w.setSuperSuper(b.getSuperSuper());
                w.setSuperMaster(b.getSuperMaster());
                w.setMaster(b.getMaster());
                w.setTransTypeId(20);
                w.setAddName("auto");
                w.setMatchId(b.getMatchId());
                w.setFancyId(b.getFancyId());
                w.setFancyResults(0);
                w.setWinnerName(luck7bResult.getWinnerName());
                w.setSeq(0);
                w.setCurrentShare(0.0);
                w.setRemainingShare(0.0);
                w.setIsActive(0);
                w.setMarketId(b.getMarketId());
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
                w.setHomeTeamName("NA");
                w.setAwayTeamName("NA");
                w.setHomeTeamId(5100);
                w.setAwayTeamId(5100);
                w.setLeagueName("NA");
                w.setChar01(b.getId());
                w.setChar02("NA");
                w.setSComm(0.0);
                w.setCMComm(0.0);
                w.setMComm(0.0);
                w.setCSComm(0.0);


                betResult = b.getBetLoss();
            }

            betTransId.add(b.getId());

            uBet = new BetTransaction();
            uBet.setId(b.getId());

            // update bet table for bet status and bet result.
            Query query = new Query(Criteria.where("matchId").is(5100).and("_id").in(b.getId()));
            Update update = new Update()
                    .set("betResult", betResult)
                    .set("winnerName", winnerTeamName)
                    .set("betStatus", 1);

            mongoTemplate.updateFirst(query, update, BetTransaction.class);


            wList.add(w);

            if (!uniqueBetUserId.contains(w.getUserId())) {
                uniqueBetUserId.add(w.getUserId());
            }

            if (!uniqueMid.contains(b.getMarketId())) {
                uniqueMid.add(b.getMarketId());
            }
        }

        }
        if(isResultExist)
        {
            winnerTeamName ="No Result";
            userWalletRepository.saveAll(wList);

            for (String mid:uniqueMid){
                user_wallet_table(mid);
                bet_table_data(mid);
            }




            for (String u : uniqueBetUserId){

                resultOdds= getAllCasinoLoad(u,0,40);



                double minValueOdds=0.0;
                if(resultOdds!=null) {
                    minValueOdds = Math.min(resultOdds.getHomeTeamPosition(), Math.min(resultOdds.getAwayTeamPosition(), resultOdds.getDrawTeamPosition()));
                }
                user=new User();
                user=userRepository.findByEmail(u);
                double winning = getUserWinningByRole(user.getEmail(),40);


                user.setCasino_Exposer(minValueOdds);
                user.setProfit(winning);
                user.setBalance(getBalanceFromWallet(u));

                uList.add(user);

                // pull uniquer non-user Id
                if(!uniqueUserId.contains(user.getMaster())){

                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getMaster());
                    uniqueUserModel.setRoleId(35);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getMaster());
                }
                if(!uniqueUserId.contains(user.getSuperMaster())){
                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getSuperMaster());
                    uniqueUserModel.setRoleId(30);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getSuperMaster());
                }
                if(!uniqueUserId.contains(user.getSuperSuper())){
                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getSuperSuper());
                    uniqueUserModel.setRoleId(25);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getSuperSuper());
                }
                if(!uniqueUserId.contains(user.getAdmin())){
                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getAdmin());
                    uniqueUserModel.setRoleId(20);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getAdmin());
                }
                if(!uniqueUserId.contains(user.getSuperAdmin())){
                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getSuperAdmin());
                    uniqueUserModel.setRoleId(15);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getSuperAdmin());
                }
                if(!uniqueUserId.contains(user.getRoot())){
                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getRoot());
                    uniqueUserModel.setRoleId(10);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getRoot());
                }
                if(!uniqueUserId.contains(user.getRootAdmin())){
                    uniqueUserModel=new UniqueUserModel();
                    uniqueUserModel.setUserId(user.getRootAdmin());
                    uniqueUserModel.setRoleId(5);
                    uniqueUserIdList.add(uniqueUserModel);
                    uniqueUserId.add(user.getRootAdmin());
                }
            }

            userRepository.saveAll(uList);
            for (UniqueUserModel uu : uniqueUserIdList) {
                double winning = getUserWinningByRole(uu.getUserId(),uu.getRoleId());
                adminUser=userRepository.findByEmail(uu.getUserId());
                adminUser.setProfit(winning);
                adminNUsersList.add(adminUser);
            }
            userRepository.saveAll(adminNUsersList);



            return "Update Successfully !!";
    }
    else
        {
        return "No updates !!";
        }
    }



    public double getUserWinningByRole(String userId, int roleId) {
        List<Integer> transId1 = Arrays.asList(3,4,20);
        Criteria criteria;
        double actualValue;

        if(roleId==35){
            criteria = Criteria.where("master").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==30){
            criteria = Criteria.where("superMaster").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==25){
            criteria = Criteria.where("superSuper").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==20){
            criteria = Criteria.where("admin").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==15){
            criteria = Criteria.where("superAdmin").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==10){
            criteria = Criteria.where("root").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==5){
            criteria = Criteria.where("rootAdmin").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else {
            criteria = Criteria.where("userId").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return totalCredits + totalDebit; // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }

    }

    public AggregatedResult getAllCasinoLoad(String userId, int betStatus, int roleId) {

        if(roleId==40){
            Criteria criteria = Criteria.where("userId").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("userId")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();

        } else if(roleId==35){
            Criteria criteria = Criteria.where("master").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("master")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==30){
            Criteria criteria = Criteria.where("superMaster").and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("superMaster")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }

        else if(roleId==25){
            Criteria criteria = Criteria.where("superSuper").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("superSuper")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }

        else if(roleId==20){
            Criteria criteria = Criteria.where("admin").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("admin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==15){
            Criteria criteria = Criteria.where("superAdmin").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("superAdmin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==10){
            Criteria criteria = Criteria.where("root").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("root")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==5){
            Criteria criteria = Criteria.where("rootAdmin").is(userId).and("matchId").is(5100).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("rootAdmin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else {
            Criteria criteria = Criteria.where("rootAdmin").is(userId).and("matchId");
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("rootAdmin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }




    }
    public double getBalanceFromWallet(String userId) {
        Criteria criteria = Criteria.where("userId").is(userId);
        double actualValue;
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group()
                        .sum("credits").as("totalCredits")
                        .sum("debit").as("totalDebit")
        );

        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                aggregation, "user-wallet", BasicDBObject.class
        );

        BasicDBObject aggregatedResult = result.getUniqueMappedResult();

        if (aggregatedResult != null) {
            double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
            double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
            return totalCredits + totalDebit; // Calculate balance as credits - debits
        } else {
            return 0.0;
        }
    }

    public ResponseEntity<String> user_wallet_table(String marketId){
        List<com.soccertennisgame.soccertennis.PostGreModel.UserWallet> wList1 = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        List<UserWallet> wtts = userWalletRepository.findByMarketId(marketId);
        for (UserWallet w1: wtts ) {
            com.soccertennisgame.soccertennis.PostGreModel.UserWallet c1 = modelMapper.map(w1, com.soccertennisgame.soccertennis.PostGreModel.UserWallet.class);
            wList1.add(c1);
        }
        userWalletPostRepository.saveAll(wList1);
        String message = "Updated";
        return ResponseEntity.ok(message);
    }


    public ResponseEntity<String> bet_table_data(String marketId) {
        ArrayList<com.soccertennisgame.soccertennis.PostGreModel.BetTransaction> wList = new ArrayList<>();
        List<BetTransaction> bets = betTransactionRepository.findByMarketId(marketId);
        ModelMapper modelMapper = new ModelMapper();
        for (BetTransaction bet : bets) {
            com.soccertennisgame.soccertennis.PostGreModel.BetTransaction w1 = modelMapper.map(bet, com.soccertennisgame.soccertennis.PostGreModel.BetTransaction.class);
            wList.add(w1);
        }

        betTransactionPostRepository.saveAll(wList);
        String message = "Updated";
        return ResponseEntity.ok(message);
    }


}

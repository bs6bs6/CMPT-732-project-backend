package com.bs6.election.controller;


import com.bs6.election.model.*;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ElectionController {
    Logger logger = LoggerFactory.getLogger(ElectionController.class);

    @Autowired
    CassandraTemplate cassandraTemplate;

    @CrossOrigin
    @GetMapping("/getBiden")
    public List<StateVO> getBiden(){
        List<StateVO> res = new ArrayList<>();
        String cql = "SELECT * FROM final.state";
        List<StateDTO> results = cassandraTemplate.select(cql, StateDTO.class);
        for(StateDTO s: results){
            res.add(new StateVO(s.getState(),s.getBiden()));
        }
        return res;
    }
    @CrossOrigin
    @GetMapping("/getTrump")
    public List<StateVO> getTrump(){
        List<StateVO> res = new ArrayList<>();
        String cql = "SELECT * FROM final.state";
        List<StateDTO> results = cassandraTemplate.select(cql, StateDTO.class);
        for(StateDTO s: results){
            res.add(new StateVO(s.getState(),s.getTrump()));
        }
        return res;
    }

    @CrossOrigin
    @GetMapping("/getCompete")
    public List<StateVO> getCompete(){
        List<StateVO> res = new ArrayList<>();
        String cql = "SELECT * FROM final.state";
        List<StateDTO> results = cassandraTemplate.select(cql, StateDTO.class);
        for(StateDTO s: results){
            if(s.getBiden()>s.getTrump()){
                res.add(new StateVO(s.getState(),0));
            }else{
                res.add(new StateVO(s.getState(),1));
            }
        }
        return res;
    }

    @CrossOrigin
    @GetMapping("/getWords")
    public WordList getWords(@RequestParam String candidate,@RequestParam String state){
        WordList res = new WordList();

        String cql = "SELECT * FROM final.word_list WHERE state = :state and candidate = :candidate ALLOW FILTERING";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .addNamedValue("state", state)
                .addNamedValue("candidate", candidate)
                .build();
        List<WordDTO> results = cassandraTemplate.select(statement, WordDTO.class);

        logger.info(results.toString());

        HashMap<String,Integer> map = new HashMap<>();
        for(WordDTO word:results){
            map.put(word.getWord(), word.getCount());
        }
        res.setCandidate(candidate);
        res.setState(state);
        res.setWords(map);
        return res;
    }

    @CrossOrigin
    @GetMapping("/getLanguageNegative")
    public List<LanguageVO> getLanguageNegative(@RequestParam String candidate){
        List<LanguageVO> res = new ArrayList<>();
        String cql = "SELECT * FROM final.language";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<LanguageDTO> results = cassandraTemplate.select(statement, LanguageDTO.class);
        if(candidate.equals("biden")){

        }else{

        }
        return res;
    }


    @CrossOrigin
    @GetMapping("/getCumulative")
    public List<CumulativeDTO> getCumulative(){
        List<CumulativeDTO> res = new ArrayList<>();
        String[] country = {
                "German",
                "Indonesian",
                "Romanian",
                "English",
                "Danish",
                "Dutch",
                "French",
                "Vietnamese",
                "Spanish"};
        HashMap<String,String> map = new HashMap<>();
        map.put("German","Germany");
        map.put("Indonesian","India");
        map.put("Romanian","Romania");
        map.put("English","United States");
        map.put("Danish","Denmark");
        map.put("Dutch","Netherlands");
        map.put("French","France");
        map.put("Vietnamese","Viet Nam");
        map.put("Spanish","Spain");

        HashSet<String> set = new HashSet<>(Arrays.asList(country));
        String cql = "SELECT * FROM final.date_cumulative";
        List<CumulativeDTO> results = cassandraTemplate.select(cql, CumulativeDTO.class);
        for(CumulativeDTO c:results){
            if(set.contains(c.getLanguage())){
                c.setLanguage(map.get(c.getLanguage()));
                res.add(c);
            }
        }
        return res;
    }

    @CrossOrigin
    @GetMapping("/getBidenHourly")
    public List<HourlyVO> getBidenHourly(){
        List<HourlyVO> res = new ArrayList<>();
        String cql = "SELECT hour,biden FROM final.hour";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<HourlyDTO> results = cassandraTemplate.select(statement, HourlyDTO.class);
        for(HourlyDTO h:results){
            res.add(new HourlyVO(h.getHour(),h.getBiden()));
        }
        res.sort((a, b) -> a.getHour() - b.getHour());
        logger.info(res.toString());
        return res;
    }

    @CrossOrigin
    @GetMapping("/getTrumpHourly")
    public List<HourlyVO> getTrumpHourly(){
        List<HourlyVO> res = new ArrayList<>();
        String cql = "SELECT hour,trump FROM final.hour";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<HourlyDTO> results = cassandraTemplate.select(statement, HourlyDTO.class);
        for(HourlyDTO h:results){
            res.add(new HourlyVO(h.getHour(),h.getTrump()));
        }
        res.sort((a, b) -> a.getHour() - b.getHour());
        logger.info(res.toString());
        return res;
    }

    @CrossOrigin
    @GetMapping("/getBidenLanguage")
    public List<LanguageVO> getBidenLanguage(){
        List<LanguageVO> res = new ArrayList<>();
        String[] language = {
                "German",
                "Indonesian",
                "Romanian",
                "English",
                "Danish",
                "Dutch",
                "French",
                "Vietnamese",
                "Spanish"};
        HashSet<String> set = new HashSet<>(Arrays.asList(language));
        String cql = "SELECT * FROM final.language";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<LanguageDTO> results = cassandraTemplate.select(statement, LanguageDTO.class);
        for(LanguageDTO l:results){
            if(set.contains(l.getLanguage())){
                res.add(new LanguageVO(l.getLanguage(),l.getBidennegative(),l.getBidenpositive(),l.getBidenneutral()));
            }
        }
        return res;
    }

    @CrossOrigin
    @GetMapping("/getTrumpLanguage")
    public List<LanguageVO> getTrumpLanguage(){
        List<LanguageVO> res = new ArrayList<>();
        String[] language = {
                "German",
                "Indonesian",
                "Romanian",
                "English",
                "Danish",
                "Dutch",
                "French",
                "Vietnamese",
                "Spanish"};
        HashSet<String> set = new HashSet<>(Arrays.asList(language));
        String cql = "SELECT * FROM final.language";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<LanguageDTO> results = cassandraTemplate.select(statement, LanguageDTO.class);
        for(LanguageDTO l:results){
            if(set.contains(l.getLanguage())){
                res.add(new LanguageVO(l.getLanguage(),l.getTrumpnegative(),l.getTrumppositive(),l.getTrumpneutral()));
            }
        }
        return res;
    }

    @CrossOrigin
    @GetMapping("/getTrumpState")
    public List<SentimentVO> getTrumpState(){
        List<SentimentVO> res = new ArrayList<>();
        String[] state = {
                "Alaska",
                "California",
                "Texas",
                "Florida",
                "New Jersey",
                "Kansas",
                "New York"
        };
        HashSet<String> set = new HashSet<>(Arrays.asList(state));
        String cql = "SELECT * FROM final.state_analyze";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<SentimentDTO> results = cassandraTemplate.select(statement, SentimentDTO.class);
        for(SentimentDTO l:results){
            if(set.contains(l.getState())){
                res.add(new SentimentVO(l.getState(),l.getTrumpnegative(),l.getTrumppositive(),l.getTrumpneutral()));
            }
        }
        return res;
    }
    @CrossOrigin
    @GetMapping("/getBidenState")
    public List<SentimentVO> getBidenState(){
        List<SentimentVO> res = new ArrayList<>();
        String[] state = {
                "Alaska",
                "California",
                "Texas",
                "Florida",
                "New Jersey",
                "Kansas",
                "New York"
        };
        HashSet<String> set = new HashSet<>(Arrays.asList(state));
        String cql = "SELECT * FROM final.state_analyze";
        SimpleStatement statement = SimpleStatement.builder(cql)
                .build();
        List<SentimentDTO> results = cassandraTemplate.select(statement, SentimentDTO.class);
        for(SentimentDTO l:results){
            if(set.contains(l.getState())){
                res.add(new SentimentVO(l.getState(),l.getBidennegative(),l.getBidenpositive(),l.getBidenneutral()));
            }
        }
        return res;
    }

}

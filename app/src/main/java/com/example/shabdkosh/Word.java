package com.example.shabdkosh;

import java.util.ArrayList;
import java.util.List;

public class Word {

    String word;
    List<ResultsData> results;
    String error;

    public void setWord(String word) {
        this.word = word;
    }
    public void setEtymology(String etymology){
        results.get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().add(etymology);
    }
    public Word(String word, List<ResultsData> results, String error) {
        this.word = word;
        this.results = results;
        this.error = error;
    }

    public Word() {
        results = new ArrayList<>();
        ResultsData sample = new ResultsData();
        results.add(sample);
    }

    public String getWord() {
        return word;
    }

    public List<ResultsData> getResults() {
        return results;
    }

    public String getError() {
        return error;
    }

    public class ResultsData {
        List<LexicalData> lexicalEntries;

        public ResultsData() {
            lexicalEntries = new ArrayList<>();
            LexicalData sample = new LexicalData();
            lexicalEntries.add(sample);
        }

        public ResultsData(List<LexicalData> lexicalEntries) {
            this.lexicalEntries = lexicalEntries;
        }

        public List<LexicalData> getLexicalEntries() {
            return lexicalEntries;
        }

        public class LexicalData {
            List<EntriesData> entries;

            public LexicalData() {
                entries = new ArrayList<>();
                EntriesData sample = new EntriesData();
                entries.add(sample);
            }

            public LexicalData(List<EntriesData> entries) {
                this.entries = entries;
            }

            public List<EntriesData> getEntries() {
                return entries;
            }

            public class EntriesData {
                List<String> etymologies;

                public EntriesData() {
                    etymologies = new ArrayList<>();
                }

                public EntriesData(List<String> etymologies) {
                    this.etymologies = etymologies;
                }

                public List<String> getEtymologies() {
                    return etymologies;
                }
            }
        }
    }
}

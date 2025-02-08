package com.desafio.itau.petlocation.infrastructure.adapter.out;

public class PositionStackResponse {
    public Data[] data;

    public static class Data {
        public String country;
        public String region;
        public String county;
        public String locality;
        public String street;
    }
}

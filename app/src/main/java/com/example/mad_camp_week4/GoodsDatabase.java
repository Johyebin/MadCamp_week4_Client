package com.example.mad_camp_week4;

import java.util.ArrayList;

public class GoodsDatabase {
    private ArrayList<GoodsItem> goodsItemArrayList = new ArrayList<>();

    // Constructor
    public GoodsDatabase() {
        // TwoSome
        goodsItemArrayList.add(new GoodsItem("10","아메리카노\n블랙그라운드","투썸","4100","145",R.drawable.twosome_americano));
        goodsItemArrayList.add(new GoodsItem("11","아메리카노\n아로마노트","투썸","4100","169",R.drawable.twosome_americano));
        goodsItemArrayList.add(new GoodsItem("12","에스프레소싱글","투썸","3300","72",R.drawable.twosome_espresso));
        goodsItemArrayList.add(new GoodsItem("13","에스프레소더블","투썸","3800","145",R.drawable.twosome_espresso));
        goodsItemArrayList.add(new GoodsItem("14","콜드브루","투썸","4500","196",R.drawable.twosome_coldbrew));
        goodsItemArrayList.add(new GoodsItem("15","카페라떼","투썸","4600","0",R.drawable.twosome_cafelatte));
        goodsItemArrayList.add(new GoodsItem("16","카라멜마키아또","투썸","5600","0",R.drawable.twosome_caramel));

        //Starbucks
        goodsItemArrayList.add(new GoodsItem("20","아메리카노","스타벅스","4100","150",R.drawable.starbucks_americano));
        goodsItemArrayList.add(new GoodsItem("21","아이스커피","스타벅스","4100","140",R.drawable.starbucks_icecoffee));
        goodsItemArrayList.add(new GoodsItem("22","에스프레소","스타벅스","3600","75",R.drawable.starbucks_espresso));
        goodsItemArrayList.add(new GoodsItem("23","에스프레소\n콘파나","스타벅스","3800","75",R.drawable.starbucks_espressocon));
        goodsItemArrayList.add(new GoodsItem("24","콜드브루","스타벅스","4500","150",R.drawable.starbucks_coldbrew));
        goodsItemArrayList.add(new GoodsItem("25","카페라떼","스타벅스","4600","75",R.drawable.starbucks_caffelatte));
        goodsItemArrayList.add(new GoodsItem("26","카라멜마키아또","스타벅스","5600","75",R.drawable.starbucks_caremelmacci));
        goodsItemArrayList.add(new GoodsItem("27","카푸치노","스타벅스","4600","75",R.drawable.starbucks_capuccino));
        goodsItemArrayList.add(new GoodsItem("28","돌체라떼","스타벅스","5600","150",R.drawable.starbucks_dolcelatte));
        goodsItemArrayList.add(new GoodsItem("29","바닐라\n플랫화이트","스타벅스","5600","260",R.drawable.starbucks_vanillaflatwhite));
        goodsItemArrayList.add(new GoodsItem("30","그린티라떼","스타벅스","5900","95",R.drawable.starbucks_greentealatte));
        goodsItemArrayList.add(new GoodsItem("31","화이트\n초콜릿모카","스타벅스","5600","75",R.drawable.starbucks_whitechocolatemocha));
        goodsItemArrayList.add(new GoodsItem("32","자바칩\n프라푸치노","스타벅스","6100","100",R.drawable.starbucks_javachip));
        goodsItemArrayList.add(new GoodsItem("33","그린티크림\n프라푸치노","스타벅스","6300","95",R.drawable.starbucks_greenteacreme));
        goodsItemArrayList.add(new GoodsItem("34","시그니처초콜릿","스타벅스","5300","15",R.drawable.starbucks_signaturechoco));
        goodsItemArrayList.add(new GoodsItem("35","쿨라임피지오","스타벅스","5900","60",R.drawable.starbucks_coollime));

        //GongCha
        goodsItemArrayList.add(new GoodsItem("40","아메리카노","공차","3500","103",R.drawable.gongcha_americano));
        goodsItemArrayList.add(new GoodsItem("41","카페라떼","공차","4000","81",R.drawable.gongcha_cafelatte));
        goodsItemArrayList.add(new GoodsItem("42","블랙밀크티","공차","4000","56",R.drawable.gongcha_black));
        goodsItemArrayList.add(new GoodsItem("43","타로밀크티","공차","4000","0",R.drawable.gongcha_taro));
        goodsItemArrayList.add(new GoodsItem("44","초콜렛밀크티","공차","4000","12",R.drawable.gongcha_chocolate));
        goodsItemArrayList.add(new GoodsItem("45","우롱밀크티","공차","4000","39",R.drawable.gongcha_ulong));
        goodsItemArrayList.add(new GoodsItem("46","얼그레이밀크티","공차","4000","73",R.drawable.gongcha_alglay));
        goodsItemArrayList.add(new GoodsItem("47","그린밀크티","공차","4000","42",R.drawable.gongcha_green));
        goodsItemArrayList.add(new GoodsItem("48","딸기쥬얼리\n밀크티","공차","5100","12",R.drawable.gongcha_strawberryjewelry));
        goodsItemArrayList.add(new GoodsItem("49","망고쥬얼리\n밀크티","공차","5100","14",R.drawable.gongcha_mango));
        goodsItemArrayList.add(new GoodsItem("50","청포도그린티\n에이드","공차","4300","56",R.drawable.gongcha_whitegrape));
        goodsItemArrayList.add(new GoodsItem("51","흑임자폼밀크티","공차","5100","32",R.drawable.gongcha_blackk));
        goodsItemArrayList.add(new GoodsItem("52","초코쿠앤크\n스무디","공차","4500","8",R.drawable.gongcha_chocosmoothie));
        goodsItemArrayList.add(new GoodsItem("53","제주그린티\n스무디","공차","5300","84",R.drawable.gongcha_jeju));

        // Etc
        goodsItemArrayList.add(new GoodsItem("70","데자와","기타","1300","55",R.drawable.etc_tejava));
        goodsItemArrayList.add(new GoodsItem("71","다방커피","기타","1500","260",R.drawable.etc_dabang));
        goodsItemArrayList.add(new GoodsItem("72","스누피커피우유","기타","1500","237",R.drawable.etc_snoopy));
        goodsItemArrayList.add(new GoodsItem("73","핫식스","기타","1200","80",R.drawable.etc_hotsix));
        goodsItemArrayList.add(new GoodsItem("74","박카스","기타","800","30",R.drawable.etc_bacca));
        goodsItemArrayList.add(new GoodsItem("75","몬스터","기타","2000","100",R.drawable.etc_monster));
        goodsItemArrayList.add(new GoodsItem("76","녹차","기타","1000","15",R.drawable.etc_greentea));
        goodsItemArrayList.add(new GoodsItem("77","콜라","기타","1200","23",R.drawable.etc_cola));
        goodsItemArrayList.add(new GoodsItem("78","초콜릿","기타","1500","16",R.drawable.etc_choco));
        goodsItemArrayList.add(new GoodsItem("79","믹스커피","기타","500","69",R.drawable.etc_mix));

    }

    // Getter
    public ArrayList<GoodsItem> getGoodsItemArrayList() {
        return goodsItemArrayList;
    }

    // Setter
    public void setGoodsItemArrayList(ArrayList<GoodsItem> goodsItemArrayList) {
        this.goodsItemArrayList = goodsItemArrayList;
    }

    public ArrayList<GoodsItem> getCafeItemArrayList(String cafeName){
        ArrayList<GoodsItem> cafeItemList = new ArrayList<>();
        for(GoodsItem g: goodsItemArrayList){
            if(g.getCafeName().equals(cafeName)){
                cafeItemList.add(g);
            }
        }
        return cafeItemList;
    }

    // findGoods
    // goodId로 해당하는 객체를 반환하는 메소드
    public GoodsItem findGoods(String goodId){
        GoodsItem tmp = new GoodsItem();
        for(GoodsItem g: goodsItemArrayList)
            if(goodId.equals(g.getGoodId()))
                tmp = g;
            return tmp;
    }

    // 카페인양을 계산해서 반환하는 메소드
    public int getCaffeineContent(ArrayList<CafeResult> argsList){
        int sum = 0;

        ArrayList<CafeResult> tmpList = argsList;
        for(int i=0;i<tmpList.size();i++){
            String goodId = tmpList.get(i).getGoodId();
            for(int j=0;j<goodsItemArrayList.size();j++)
                if(goodId.equals(goodsItemArrayList.get(j).getGoodId()))
                    sum += Integer.parseInt(goodsItemArrayList.get(j).getCaffeineContent());
        }
        return sum;
    }


    // 가격을 계산해서 반환하는 메소드
    public int getPrice(ArrayList<CafeResult> argsList){
        int sum = 0;

        ArrayList<CafeResult> tmpList = argsList;
        for(int i=0;i<tmpList.size();i++){
            String goodId = tmpList.get(i).getGoodId();
            for(int j=0;j<goodsItemArrayList.size();j++)
                if(goodId.equals(goodsItemArrayList.get(j).getGoodId()))
                    sum += Integer.parseInt(goodsItemArrayList.get(j).getPrice());
        }
        return sum;
    }


}

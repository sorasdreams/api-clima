package sorasdreams.apiclima.model;

public enum Language {

    ES,
    EN,
    PT,
    DE,
    FR,
    IT,
    RU,
    TR,
    HI;

    public static boolean isValidLanguage(String lang){
        for(Language l : values()){
           if(l.name().equals(lang)){
               return true;
           }
        }
        return false;
    }


}

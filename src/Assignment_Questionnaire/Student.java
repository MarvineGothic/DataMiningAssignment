package Assignment_Questionnaire;

import Assignment_Questionnaire.enums.*;
import Assignment_Questionnaire.enums.Number;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static Assignment_Questionnaire.Library.RU.getMethod;
import static Assignment_Questionnaire.Library.RU.getValueByName;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("all")
public class Student {
    public TimeStamp s_timeStamp;                                                        //n
    public Age s_age;                                                                   //y
    public Gender s_gender;                                                             //y
    public ShoeSize s_shoeSize;                                                         //y
    public Height s_height;                                                             //y
    public Degree s_degree;                                                             //y
    public ReasonTakeCourse s_reasonTakingCourse;                                       //yn
    public ProgrammingLanguage[] s_programmingLanguages;                                  //yn
    public PhoneOS s_phoneOS;                                                           //yn

    public Topic_DED s_topicDED;
    public Topic_CPM s_topicCPM;
    public Topic_DGS s_topicDGS;
    public Topic_VD s_topicVD;
    public Topic_SPS s_topicSPS;
    public Topic_SPSQ s_topicSPSQ;
    public Topic_SPG s_topicSPG;
    public Topic_SPT s_topicSPT;
    public Topic_SPI s_topicSPI;
    public Topic_CDMA s_topicCDMA;
    public Topic_UDMT s_topicUDMT;


    public GamesPlayed[] s_gamesPlayed;                                                   //yn
    public CommuteToITU[] s_commuteToITU;                                                  //n
    public TraverseITU[] s_traverseITU;                                                    //n
    public FourNumbers[] s_fourNumbers;                                                   //n
    public Therb s_therb;                                                                 //n
    public Number s_number;                                                                //n
    public FavoriteFilm[] s_favFilm;                                                                 //n
    public TVShow[] s_favTVshow;                                                            //n
    public FavoriteGame[] s_favGame;                                                                 //n
    public Row s_row;                                                                      //y
    public Seat s_seat;                                                                     //y

    public static ArrayList<Object> getAttributeList() {
        ArrayList<Object> attributeList = new ArrayList<>();
        attributeList.add(Age.class);
        attributeList.add(Gender.class);
        attributeList.add(ShoeSize.class);
        attributeList.add(Height.class);
        attributeList.add(Degree.class);
        attributeList.add(ReasonTakeCourse.class);
        attributeList.add(PhoneOS.class);

        attributeList.add(Topic_DED.class);
        attributeList.add(Topic_CPM.class);
        attributeList.add(Topic_DGS.class);
        attributeList.add(Topic_VD.class);
        attributeList.add(Topic_SPS.class);
        attributeList.add(Topic_SPSQ.class);
        attributeList.add(Topic_SPG.class);
        attributeList.add(Topic_SPT.class);
        attributeList.add(Topic_SPI.class);
        attributeList.add(Topic_CDMA.class);
        attributeList.add(Topic_UDMT.class);

        attributeList.add(Therb.class);
        attributeList.add(Number.class);
        attributeList.add(FavoriteFilm.class);
        attributeList.add(TVShow.class);
        attributeList.add(FavoriteGame.class);
        attributeList.add(Row.class);
        attributeList.add(Seat.class);
        return attributeList;
    }

    public Object getAttributeValue(Object attribute) {

        String field = "";
        Field[] fields = this.getClass().getFields();
        for (Field f : fields) {
            if (f.getType().isArray() && !(attribute instanceof String))
                attribute = ((Class) attribute).getSimpleName();
            if (attribute instanceof String) {
                String s = f.getType().getSimpleName().replaceAll("]|\\[", "");
                if (s.equals(attribute)) {
                    field = f.getName();
                    break;
                }
            } else if (f.getType() == attribute) {
                field = f.getName();
                break;
            }
        }
        return getValueByName(field, this);
    }

    /**
     * Method to get double value from Student class attributes
     * to normalize it afterwards and use in clustering
     *
     * @param name
     * @return
     */
    public double getAnyAttribute(String name) {
        double length = 0;
        Object object = this.getAttributeValue(name);
        Class clazz = object.getClass();
        if (clazz.isArray()) {
            length = ((Object[]) object).length;
            return Arrays.toString((Object[]) object).matches("\\[I have not played any of these games]|\\[NONE]") ? 0 : length;
        }
        // handling all topics
        if (clazz.getSimpleName().contains("Topic_")) {
            return (int) getMethod("getRate", object);
        }
        return length;
    }
}

package com.ohgiraffers.mtvsreserve.reservation.controller;

import com.ohgiraffers.mtvsreserve.reservation.dto.TableInfoDTO;
import com.ohgiraffers.mtvsreserve.reservation.repository.ReservationTableRepository;
import com.ohgiraffers.mtvsreserve.reservation.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.io.IOException;

@Transactional
@SpringBootTest
class reserveServiceTest {
    /*
    MOCK
    (가상 객체를 넘겨주고)
    STUB
    (가짜 객체...)
     */
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationTableRepository reservationTableRepository;

    @DisplayName("저장이 되는지 확인")
    @Test
    public void savetest() {

        /*
    private Long id;
    private String date;
    private String roomNum;
    private int timeNum;
    private String userId;
         */
        TableInfoDTO tableInfoDTO = new TableInfoDTO(3L,"2023-06-28","1",4,"test1" );
        int cursize = reservationTableRepository.findAll().size();
        System.out.println(cursize);

        reservationService.saveForTest(tableInfoDTO);

        int aftersize = reservationTableRepository.findAll().size();
        Assertions.assertEquals(cursize+1,aftersize);
    }

    @DisplayName("3개 이상 예약 안되는지 확인")
    @Test
    public void saveExceptiontest() {
        /*
    private Long id;
    private String date;
    private String roomNum;
    private int timeNum;
    private String userId;
         */
        TableInfoDTO tableInfoDTO1 = new TableInfoDTO(3L,"2023-06-28","1",4,"test1" );
        TableInfoDTO tableInfoDTO2 = new TableInfoDTO(4L,"2023-06-28","2",4,"test1" );
        TableInfoDTO tableInfoDTO3 = new TableInfoDTO(5L,"2023-06-28","3",4,"test1" );


        reservationService.saveForTest(tableInfoDTO1);
        reservationService.saveForTest(tableInfoDTO2);

        Assertions.assertThrows(
                IllegalStateException.class,()-> reservationService.saveForTest(tableInfoDTO3)
        );
    }

    /*
        public List<TableInfoDTO> findCompleteReserve(String date, int roomNum) {
        String[] dates = date.split("-");
        String final_date = dates[0] + dates[1] + dates[2];

        List<ReservationTableEntity> reservationTableEntityList = reservationTableRepository.findAll();
        List<TableInfoDTO> tableInfoDTOList = new ArrayList<>();
        String roomN=String.valueOf(roomNum);
        int j = 0;
        for (ReservationTableEntity reservationTableEntity : reservationTableEntityList) {
            if (reservationTableEntityList.get(j).getDate().equals(final_date)&&(reservationTableEntityList.get(j).getRoomNum().equals(roomN))){
                tableInfoDTOList.add(TableInfoDTO.toTableInfoDTO(reservationTableEntity));
            }
            j++;
        }
        return tableInfoDTOList;
    }
     */

    @Test
    @DisplayName("날짜와 방번호에 맞는 예약 정보 조회 확인")
    public void findCompleteReserveTest(){
        TableInfoDTO tableInfoDTO1 = new TableInfoDTO(3L,"2023-06-28","1",4,"test1" );
        TableInfoDTO tableInfoDTO2 = new TableInfoDTO(4L,"2023-06-28","1",5,"test1" );

        String testDate= tableInfoDTO1.getDate();
        String testRoomnum = tableInfoDTO1.getRoomNum();
        int cursize= reservationService.findCompleteReserve(testDate,testRoomnum).size();
        System.out.println("cursize = " + cursize);


        System.out.println("testRoomnum = " + testRoomnum);
        System.out.println("testDate = " + testDate);

        reservationService.saveForTest(tableInfoDTO1);
        reservationService.saveForTest(tableInfoDTO2);

       int aftersize = reservationService.findCompleteReserve(testDate,testRoomnum).size();
        System.out.println("aftersize = " + aftersize);

        Assertions.assertEquals(cursize+2,aftersize);
    }


    @Test
    @DisplayName("모든 예약 정보가 가져와지는지 확인")
    public void findReserveTest(){

        int cursize  =  reservationService.findReserve().size();

        TableInfoDTO tableInfoDTO1 = new TableInfoDTO(3L,"2023-06-28","1",4,"test1" );
        TableInfoDTO tableInfoDTO2 = new TableInfoDTO(4L,"2023-06-28","2",5,"test1" );
        TableInfoDTO tableInfoDTO3 = new TableInfoDTO(5L,"2023-06-28","3",4,"test3" );
        TableInfoDTO tableInfoDTO4 = new TableInfoDTO(6L,"2023-06-28","4",5,"test3" );
        TableInfoDTO tableInfoDTO5 = new TableInfoDTO(7L,"2023-06-28","5",4,"test4" );
        TableInfoDTO tableInfoDTO6 = new TableInfoDTO(8L,"2023-06-28","6",5,"test4" );

        reservationService.saveForTest(tableInfoDTO1);
        reservationService.saveForTest(tableInfoDTO2);
        reservationService.saveForTest(tableInfoDTO3);
        reservationService.saveForTest(tableInfoDTO4);
        reservationService.saveForTest(tableInfoDTO5);
        reservationService.saveForTest(tableInfoDTO6);

        int aftersize = reservationService.findReserve().size();

        Assertions.assertEquals(cursize+6,aftersize);
    }

/*
    public List<TableInfoDTO> viewAllReservation(String userId) {
        List<Optional<ReservationTableEntity>> reservationTableEntities = reservationTableRepository.findAllByuserId(userId);
        List<TableInfoDTO> tableInfoDTOS=new ArrayList<>();

        for (Optional<ReservationTableEntity> t: reservationTableEntities){
            tableInfoDTOS.add(TableInfoDTO.toTableInfoDTO(t.get()));
        }

        if(tableInfoDTOS.isEmpty()){
            return null;
        }else{
            return tableInfoDTOS;
        }

    }
 */

    @Test
    @DisplayName("예약자가 예약한 정보가 잘 저장되어 있는지 확인")
    public void viewAllReservationTest(){
        TableInfoDTO tableInfoDTO1 = new TableInfoDTO(3L,"2023-06-28","1",4,"test1" );
        reservationService.saveForTest(tableInfoDTO1);
        int cursize = reservationService.viewAllReservation("test1").size();
        TableInfoDTO tableInfoDTO2 = new TableInfoDTO(4L,"2023-06-28","2",5,"test1" );
        reservationService.saveForTest(tableInfoDTO2);


        int aftersize = reservationService.viewAllReservation("test1").size();

        Assertions.assertEquals(cursize+1,aftersize);
    }

    @Test
    @DisplayName("예약 정보 삭제가 정상적으로 처리되는지 확인")
    public void deleteByIdTest(){
        TableInfoDTO tableInfoDTO1 = new TableInfoDTO(3L,"2023-06-28","1",4,"test1" );
        TableInfoDTO tableInfoDTO2 = new TableInfoDTO(4L,"2023-06-28","2",5,"test1" );
        reservationService.saveForTest(tableInfoDTO2);
        reservationService.saveForTest(tableInfoDTO1);

        int cursize = reservationService.viewAllReservation("11").size();
        System.out.println("cursize = " + cursize);
        reservationService.deleteById(1L);

        int aftersize = reservationService.viewAllReservation("11").size();
        System.out.println("aftersize = " + aftersize);
        Assertions.assertEquals(cursize -1 ,aftersize);

    }
}
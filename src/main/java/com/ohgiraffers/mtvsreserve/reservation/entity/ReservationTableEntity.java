package com.ohgiraffers.mtvsreserve.reservation.entity;

import com.ohgiraffers.mtvsreserve.reservation.dto.TableInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reserve_table")
public class ReservationTableEntity {
    // 예약 완료된 스터디룸 데이터 저장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예약 한 사람 loginId
    @Column
    private  String userId;

    // 예약 룸
    @Column
    private String roomNum;

    // 예약 날짜
    @Column
    private String date;

    // 예약 시간
    @Column
    private int timeNum;

    public static ReservationTableEntity toReservationTableEntity(TableInfoDTO tableInfoDTO) {
        ReservationTableEntity reservationTableEntity = new ReservationTableEntity();
        // Dto를 entity로 변환하는 과정임
        reservationTableEntity.setDate(tableInfoDTO.getDate());
        reservationTableEntity.setRoomNum(tableInfoDTO.getRoomNum());
        reservationTableEntity.setTimeNum(tableInfoDTO.getTimeNum());
        reservationTableEntity.setUserId(tableInfoDTO.getUserId());
        return reservationTableEntity;
    }
    public static ReservationTableEntity toUpdateReservationTableEntity(TableInfoDTO tableInfoDTO) {
        ReservationTableEntity reservationTableEntity = new ReservationTableEntity();
        // Dto를 entity로 변환하는 과정임
        reservationTableEntity.setId(tableInfoDTO.getId());
        reservationTableEntity.setDate(tableInfoDTO.getDate());
        reservationTableEntity.setRoomNum(tableInfoDTO.getRoomNum());
        reservationTableEntity.setTimeNum(tableInfoDTO.getTimeNum());
        reservationTableEntity.setUserId(tableInfoDTO.getUserId());
        return reservationTableEntity;
    }
}

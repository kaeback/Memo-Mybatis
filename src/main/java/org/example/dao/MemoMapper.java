package org.example.dao;

import org.example.model.Member;
import org.example.model.ReceiveMemoRecord;
import org.example.model.SendMemoRecord;

import java.util.Map;

public interface MemoMapper {
    // 회원정보 등록
    int saveMember(Member member);

    // 로그인
    Member findMember(Member member);

    // 보낸 메모 저장
    int saveSendMemoRecord(SendMemoRecord sendMemoRecord);

    // 받은 메모 저장
    int saveReceiveMemoRecord(ReceiveMemoRecord receiveMemoRecord);

    // 보낸 메모 읽기
    SendMemoRecord findSendMemoRecordById(long send_id);

    // 받은 메모 읽기
    ReceiveMemoRecord findReceiveMemoRecordById(long receive_id);

    // 보낸 메모 전체목록(검색)
    Map<String, Object> findSendMemoRecords(String title);

    // 받은 메모 전체목록(검색)
    Map<String, Object> findReceiveMemoRecords(String title);

    // 받은 메모 삭제
    int removeReceiveMemoRecordById(long receive_id);
}

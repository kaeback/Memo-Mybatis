package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.config.MybatisConfig;
import org.example.exception.MemberNotFoundException;
import org.example.model.Member;
import org.example.model.Memo;
import org.example.model.ReceiveMemoRecord;
import org.example.model.SendMemoRecord;

import java.util.List;
import java.util.Map;

public class MemoDao {

    private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();

    // 회원정보 등록
    public boolean saveMember(Member member) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            Member findMember = mapper.findMemberByEmail(member.getEmail());
            if (findMember == null) {
                mapper.saveMember(member);
                session.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 로그인
    public Member login(Member member) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findMember(member);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 이메일로 회원정보 검색
    public Member findMemberByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findMemberByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 쪽지 보내기
    public void sendMemo(Memo memo, long sender_id, long receiver_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            // 쪽지 저장
            mapper.saveMemo(memo);

            // 보낸 쪽지 내역 저장
            SendMemoRecord sendMemoRecord = new SendMemoRecord(memo.getMemo_id(), sender_id, receiver_id);
            mapper.saveSendMemoRecord(sendMemoRecord);

            // 받은 쪽지 내역 저장
            ReceiveMemoRecord receiveMemoRecord = new ReceiveMemoRecord(memo.getMemo_id(), sender_id, receiver_id);
            mapper.saveReceiveMemoRecord(receiveMemoRecord);

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 보낸 쪽지 전체목록
    public List<Map<String, Object>> findSendMemos(long sender_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findSendMemos(sender_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 쪽지 전체목록
    public List<Map<String, Object>> findReceiveMemos(long receiver_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findReceiveMemos(receiver_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 쪽지 읽기
    public Map<String, Object> findReceiveMemoById(long receive_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            // 받은 쪽지 읽기
            Map<String, Object> receiveMemo = mapper.findReceiveMemoById(receive_id);

            // 읽지 않은 쪽지이면 확인여부 'N' -> 'Y'로 변경
            if (receiveMemo != null && receiveMemo.get("IS_READ").equals("N")) {
                mapper.updateReceiveMemo(receive_id);
                session.commit();
            }

            return receiveMemo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

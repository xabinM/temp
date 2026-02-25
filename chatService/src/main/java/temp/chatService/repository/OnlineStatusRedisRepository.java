package temp.chatService.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
@RequiredArgsConstructor
public class OnlineStatusRedisRepository {

    private static final String KEY_PREFIX = "online:";
    private static final long TTL_SECONDS = 3600;

    private final StringRedisTemplate redisTemplate;

    public void setOnline(Long roomId, String userId) {
        redisTemplate.opsForValue().set(key(roomId, userId), "1", TTL_SECONDS, TimeUnit.SECONDS);
    }

    public void setOffline(Long roomId, String userId) {
        redisTemplate.delete(key(roomId, userId));
    }

    public boolean isOnline(Long roomId, String userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key(roomId, userId)));
    }

    private String key(Long roomId, String userId) {
        return KEY_PREFIX + roomId + ":" + userId;
    }
}

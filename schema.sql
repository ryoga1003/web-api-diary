-- 短文日記テーブル（手動で実行してください）
CREATE TABLE IF NOT EXISTS diaries (
                                       id         BIGSERIAL PRIMARY KEY,
                                       content    VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- サンプルデータ（短文日記）
INSERT INTO diaries (content, created_at) VALUES
('今日は授業でSpring Bootの基礎を学んだ。少し難しかったけど面白い。', '2026-10-15 21:30:00'),
('学食のカレーがおいしかった。また明日も食べたい。', '2026-10-16 12:45:00'),
('友達とレポートの勉強会をした。分からないところを教え合えて助かった。', '2026-10-17 19:10:00'),
('朝から雨だったので、家でゆっくり本を読んだ。', '2026-10-18 15:00:00'),
('中間課題のAPIが動いた！画面から日記が登録できて感動。', '2026-10-19 22:05:00');
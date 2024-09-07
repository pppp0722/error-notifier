INSERT INTO noti_group
VALUES ('11111111-1111-1111-1111-111111111111', 'test_name1', 'test_desc1', NOW(), NOW()),
       ('22222222-2222-2222-2222-222222222222', 'test_name2', 'test_desc2', NOW(), NOW());

INSERT INTO users
VALUES ('11111111-1111-1111-1111-111111111111', 'test_name1', 'test_token1', 'test_channel_id1', NOW(), NOW()),
       ('22222222-2222-2222-2222-222222222222', 'test_name2', 'test_token2', 'test_channel_id2', NOW(), NOW());

INSERT INTO noti_group_user
VALUES ('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111',
        '11111111-1111-1111-1111-111111111111', NOW(), NOW());
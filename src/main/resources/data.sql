-- AUTO_INCREMENT 초기화
ALTER TABLE product AUTO_INCREMENT = 1;

-- 데이터가 하나도 없을 전체 삽입
INSERT INTO product (name, brand, made_in, price)
SELECT * FROM (
                  SELECT 'Galaxy S6', 'Samsung Corp', 'Korea', 600.0 UNION ALL
                  SELECT 'Galaxy S8', 'Samsung Corp', 'Korea', 800.0 UNION ALL
                  SELECT 'Galaxy S10', 'Samsung Corp', 'Korea', 1000.0 UNION ALL
                  SELECT 'Galaxy S21', 'Samsung Corp', 'Korea', 1000.0 UNION ALL

                  SELECT 'MacBook Air1', 'Apple', 'China', 10000 UNION ALL
                  SELECT 'MacBook Air2', 'Apple', 'China', 10000 UNION ALL
                  SELECT 'MacBook Air3', 'Apple', 'China', 10000 UNION ALL
                  SELECT 'MacBook Air4', 'Apple', 'China', 10000 UNION ALL
                  SELECT 'MacBook Air5', 'Apple', 'China', 10000 UNION ALL
                  SELECT 'MacBook Pro1', 'Apple', 'China', 15000 UNION ALL
                  SELECT 'MacBook Pro2', 'Apple', 'China', 15000 UNION ALL

                  SELECT 'iPad Air', 'Apple', 'China', 500 UNION ALL
                  SELECT 'iPad Pro', 'Apple', 'China', 800 UNION ALL

                  SELECT '소나타', 'Hyundai', 'Japan', 20000 UNION ALL
                  SELECT '그랜저', 'Hyundai', 'Japan', 30000 UNION ALL
                  SELECT '제너시스', 'Hyundai', 'Japan', 50000 UNION ALL
                  SELECT '에쿠스', 'Hyundai', 'Japan', 60000 UNION ALL

                  SELECT 'Accord', 'Honda', 'Japan', 25000 UNION ALL
                  SELECT 'sienna', 'Honda', 'Japan', 40000 UNION ALL

                  SELECT 'Camry', 'Toyota', 'Japan', 25000 UNION ALL
                  SELECT 'Lexus', 'Toyota', 'Japan', 50000
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM product);
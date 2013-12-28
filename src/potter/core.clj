;;
;; Implementation of the Potter-Kata
;; @see http://codingdojo.org/cgi-bin/wiki.pl?KataPotter
;;
(ns potter.core
  (:gen-class))

(defn map-dec [m]
  (reduce-kv #(if (> %3 1)
                (assoc %1 %2 (dec %3))
                %1) {} m))

(defn sum-vals [m]
  (reduce-kv #(+ %1 %3) 0 m))

(defn discount [n]
  (nth [0M (* 1 8M) (* 2 8 0.95M) (* 3 8 0.9M) (* 4 8 0.8M) (* 5 8 0.75M)] n))

(defn calculate-price [books]
  (let [books-map (frequencies books)]
    (loop [books-map books-map
           price 0M
           fivers-count 0]
      (let [book-count (sum-vals books-map)
            distinct-books (count (keys books-map))
            fiver (if (= 5 distinct-books) 1 0)]
        (cond
         (= 0 book-count)
           price
         (and (> fivers-count 0) (= 3 distinct-books))
           ; Special case 2 times 4 books are cheaper than 5 and 3 books.
           ; this is corrected with this term
           (recur (map-dec books-map) (+ (- price (discount 5)) (* 2 (discount 4))) (dec fivers-count))
         :else
           (recur (map-dec books-map) (+ price (discount distinct-books)) (+ fivers-count fiver)))))))

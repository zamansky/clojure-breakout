(ns  breakout
  (:require [reagent.core :as r]
            ))


(def colors ["blue" "red" "yellow" "green" "orange" "white" "black"])

(def sample-state {:paddle {:x 250 :y 310 :width 50 :height 10}
                   :ball {:x 200 :y 140 :dx 1 :dy -1}
                   :speed 20
                   :bricks {}
                   :width 400
                   :height 400
                   :brickstart 50
                   :rows 5
                   :live? true
                   :running? true
                   })

(defrecord brick [id x y width height color])

(defn ball-hits-brick? [ball brick]
  (let [bx (:x ball)
        by (:y ball)
        brick-x1 (:x brick)
        brick-y1 (:y brick)
        brick-x2 (+ brick-x1 (:width brick))
        brick-y2 (+ brick-y1 (:height brick))
        ]
    (and
     (> bx brick-x1)
     (< bx brick-x2)
     (> by brick-y1)
     (< by brick-y2)
     )
    )
  )


(defn move-ball [state]
(let [
      ball (:ball state)
      newx (+ (:x ball) (:dx ball))
      newy (+ (:y ball) (:dy ball))
      ns1 (assoc-in state [:ball :x] newx)
      ns2 (assoc-in ns1 [:ball :y] newy)
      ]
ns2
))

(defn ball-paddle-interaction [state]
(let [ball (:ball state)
      paddle (:paddle state)]
(if (and (=  (:y ball)(:y paddle))
         (>= (:x ball) (:x paddle))
         (<= (:x ball) (+ (:x paddle) (:width paddle))))
  (let [ndy (* -1 (:dy ball))
        ndx (if (< (:x ball) (+ (:x paddle) (/ (:width paddle) 2)))
              (- (:dx ball) 2)
              (+ (:dx ball) 2))
        newball (assoc ball :dx ndx :dy ndy)
        ]
    (print newball)
    (let [ns (assoc state :ball newball)]
      ns
      )
    )
  state
  )))

(defn ball-wall-interaction [state]
(let [ball (:ball state)
      x (:x ball)
      y (:y ball)
      dx (:dx ball)
      dy (:dy ball)
      ndx (if (or (< x 1) (> x 400))
            (* -1 dx)
            dx)
      ndy (if (or (< y 1) (> y 400))
            (* -1 dy)
            dy)
      newball (assoc ball :dx ndx :dy ndy)
      newstate (assoc state :ball newball)
      ]
newstate
))

(defn ball-brick-interaction [state]
  (let [bricks (:bricks state)
        ball (:ball state)
        hits (filter #(ball-hits-brick? ball %) bricks)
        
        ]
    (if (= 1 (count hits))
      (do
        (print hits))
      
      )
    (let [newbricks (if (= 1 (count hits))
                      (remove #(= (first hits) %) bricks)
                      bricks)
          bdy   (if (= 1 (count hits)) (* -1 (:dy ball)) (:dy ball))
          newball (assoc ball :dy bdy )
          ]
      (assoc state :bricks newbricks :ball newball)
      )
    ))

(defn runturn [state]
  (let [moved (move-ball state)
        paddle-hit-state (ball-paddle-interaction moved)
        ball-wall-state (ball-wall-interaction paddle-hit-state)
        newbricks (ball-brick-interaction ball-wall-state)
        ]
    newbricks
    ))




(defn generate-bricks [] 
(let [brickwidth (/ 400 10)
      brickheight 20]
  (for [row (range 3) col (range 10)]
    (->brick (+ (* row 10) col) (* col brickwidth) (+ 50 (* row brickheight)) brickwidth brickheight (rand-nth colors))
    )

  )
)

(defn create-initial-state []
(assoc sample-state :bricks (generate-bricks))
)

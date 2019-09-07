(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]
            [breakout :as b]
            [canvas :as c]
            ))

(def state (r/atom (b/create-initial-state)))


(defn game-loop [state]
  (go-loop [] 
    (<! (timeout (:speed @state)))
    (swap!  state b/runturn @state)
    
    (if (:running? @state)
      (recur))
    ))

(defn toggle-game [state]
  (let [running? (:running? @state)
        ]
    (if running?
      (swap! state assoc :running? false)
      (do
        (swap! state assoc :running? true)
        (game-loop state)
        )
      )
    ))

(defn draw-cells [component state]
  (let [canvas (c/get-canvas-from-component component)
        ctx (c/get-ctx canvas)
        bricks (:bricks @state)
        ball (:ball @state)
        paddle (:paddle @state)]
    (set! (. ctx -fillStyle) (str "white"))
    (c/rect ctx 0 0 400 400 "white" "white")
    ;; draw bricks
    (doseq [ {:keys [x y width height color :as bricks ]} bricks]
      (c/rect ctx x y width height color "black")
      )
    ;; draw ball
    (c/rect ctx (:x ball) (:y ball) 5 5 "black" "black")
    ;; draw paddle
    (c/rect ctx (:x paddle) (:y paddle) (:width paddle) (:height paddle) "green" "green")
    
    )
  )



(defn move-paddle [state event]
  (let [canvas (.getElementById js/document "c")
        rect (.getBoundingClientRect canvas)
        x (- (.-clientX event) (.-left rect))
        y (- (.-clientY event) (.-top rect))
        paddle (:paddle @state)
        new-paddle (assoc paddle :x x)
        ]
    (swap! state assoc-in [:paddle :x] x)
    (swap! state assoc :x x)
    
    )
  )
(defn canvas [state]
  (let []
    (r/create-class
     {:display-name "canvas"
      :component-did-mount #(draw-cells % state)
      :component-did-update #(draw-cells % state)
      :reagent-render (fn  [state]
                        @state

                        [:canvas
                         {
                          :on-mouseMove #(move-paddle state %)
                          :id "c" :width 400 :height 350 :style {:border "2px solid green"}}]
                        )
      }
     )))





(defn main-component []
  [:div 
   [:h1 "This is the component"]
   [:hr]
   [canvas state]
   [:hr]
   [:button.button.is-primary {:on-click #(toggle-game state)}"hello"]
   ])


(defn mount [c]
  (r/render-component [c] (.getElementById js/document "app"))
  )

(defn reload! []
  (mount main-component)
  (print "Hello reload!"))

(defn main! []
  (mount main-component)
  (print "Hello Main"))

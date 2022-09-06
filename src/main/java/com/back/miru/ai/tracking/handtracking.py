import cv2
import mediapipe

def main():
    cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)

    medhands = mediapipe.solutions.hands
    hands = medhands.Hands(max_num_hands=1, min_detection_confidence=0.7)

    while True:
        success, img = cap.read()
        img = cv2.flip(img, 1)
        imgrgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        res = hands.process(imgrgb)

        lmlist = []
        tipids = [4, 8, 12, 16, 20]

        if res.multi_hand_landmarks:
            for handlms in res.multi_hand_landmarks:
                for id, lm in enumerate(handlms.landmark):

                    h, w, c = img.shape
                    cx, cy = int(lm.x * w), int(lm.y * h)
                    lmlist.append([id, cx, cy])
                    if len(lmlist) != 0 and len(lmlist) == 21:
                        fingerlist = []

                        if lmlist[12][1] > lmlist[20][1]:
                            if lmlist[tipids[0]][1] > lmlist[tipids[0] - 1][1]:
                                fingerlist.append(1)
                            else:
                                fingerlist.append(0)
                        else:
                            if lmlist[tipids[0]][1] < lmlist[tipids[0] - 1][1]:
                                fingerlist.append(1)
                            else:
                                fingerlist.append(0)

                        for id in range(1, 5):
                            if lmlist[tipids[id]][2] < lmlist[tipids[id] - 2][2]:
                                fingerlist.append(1)
                            else:
                                fingerlist.append(0)

                        if len(fingerlist) != 0:
                            fingercount = fingerlist.count(1)

                        if(fingercount == 5):
                            print("TEST")
                            return "MJ"

if __name__ == "__main__":
   print(main())
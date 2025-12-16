import Arrival
public static class Bus{

        private final String numberBus;
        private int countArrivals = 0;
        private final Arrival[] arrayArrival;

        // Конструктор автобуса
        public Bus(String numberBus, int maxCountArrivals){

            this.numberBus = numberBus;
            arrayArrival = new Arrival[maxCountArrivals];

        }

        // Добавление времени остановки автобуса
        public void addTimeArrivals(String time){
            if(countArrivals < arrayArrival.length){
                arrayArrival[countArrivals] = new Arrival(time);
                countArrivals++;
                sortArrivals();
            }
        }

        // Удаление времени остановки автобуса
        public void deleteTimeArrivals(String time){
            int j = 0;
            for(int i = 0; i < countArrivals; i++){
                if (time.equals(arrayArrival[i].toString())){
                    j = i;
                }
            }
            for(int i = j; i < countArrivals-1; i++){
                arrayArrival[i] = arrayArrival[i+1];
            }
            countArrivals--;
            sortArrivals();
        }

        @Override
        public String toString(){
            String res = numberBus + " : ";
            for(int i = 0; i < countArrivals; i++) {
                if(i + 1 < countArrivals) {
                    res = res + arrayArrival[i].toString() + ", ";
                }
                else{
                    res = res + arrayArrival[i].toString();
                }
            }
            return res;
        }

        // Сортировка времени методом пузырька
        public void sortArrivals(){
            Arrival p;
            for (int i = countArrivals-1; i > 0;i--){
                for (int j = countArrivals - 1; j > 0;j--){
                    if(arrayArrival[j].getIntHours() < arrayArrival[j-1].getIntHours() || (arrayArrival[j].getIntHours() == arrayArrival[j-1].getIntHours() && arrayArrival[j].getIntMinutes() < arrayArrival[j-1].getIntMinutes())){
                        p = arrayArrival[j-1];
                        arrayArrival[j-1] = arrayArrival[j];
                        arrayArrival[j] = p;
                    }
                }
            }
        }

        // Добовление прибавленного времени
        public void addSumTime(String numberBus,String time){
            int countHourTime = (time.charAt(0) - '0')*10 + (time.charAt(1) - '0');
            int countMinutesTime = (time.charAt(3) - '0')*10 + (time.charAt(4) - '0');
            String resTime;
            int hour = arrayArrival[countArrivals-1].getIntHours() + countHourTime;
            int minutes = arrayArrival[countArrivals-1].getIntMinutes() + countMinutesTime;
            if(minutes >= 60){
                hour++;
                minutes -= 60;
            }
            if(hour < 10){
                if(minutes >= 10) {
                    resTime = "0" + String.valueOf(hour) + ":" + String.valueOf(minutes);
                }
                else {
                    resTime = "0" + String.valueOf(hour) + ":0" + String.valueOf(minutes);
                }
            }
            else {
                if(minutes >= 10) {
                    resTime = String.valueOf(hour) + ":" + String.valueOf(minutes);
                }
                else {
                    resTime = String.valueOf(hour) + ":0" + String.valueOf(minutes);
                }
            }
            addTimeArrivals(resTime);
        }

        // Суммирование времени
        public String sumTime(String numberBus,String time){
            int countHourTime = (time.charAt(0) - '0')*10 + (time.charAt(1) - '0');
            int countMinutesTime = (time.charAt(3) - '0')*10 + (time.charAt(4) - '0');
            String resTime;
            int hour = arrayArrival[countArrivals-1].getIntHours() + countHourTime;
            int minutes = arrayArrival[countArrivals-1].getIntMinutes() + countMinutesTime;
            if(minutes >= 60){
                hour++;
                minutes -= 60;
            }
            if(hour < 10){
                if(minutes >= 10) {
                    resTime = "0" + String.valueOf(hour) + ":" + String.valueOf(minutes);
                }
                else {
                    resTime = "0" + String.valueOf(hour) + ":0" + String.valueOf(minutes);
                }
            }
            else {
                if(minutes >= 10) {
                    resTime = String.valueOf(hour) + ":" + String.valueOf(minutes);
                }
                else {
                    resTime = String.valueOf(hour) + ":0" + String.valueOf(minutes);
                }
            }
            return resTime;
        }




    }

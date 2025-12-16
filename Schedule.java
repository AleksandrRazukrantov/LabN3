import Arrival
import Bus
public static class Schedule {

        private int countBuses = 0;
        private final Bus[] arrayBus;
        private final String[] arrayNumbersBuses;

        public Schedule(int maxCountBuses) {
            arrayBus = new Bus[maxCountBuses];
            arrayNumbersBuses = new String[maxCountBuses];
        }

        // Добовление автобуса
        public void addBusArrivals(String numberBus, int maxCountArrivals) {
            if (countBuses < arrayBus.length) {
                arrayBus[countBuses] = new Bus(numberBus, maxCountArrivals);
                arrayNumbersBuses[countBuses] = (numberBus);
                countBuses++;
            }
        }

        // Добовление уже существующего автобуса
        public void addBusArrivals(Bus bus) {
            if (countBuses < arrayBus.length) {
                arrayBus[countBuses] = bus;
                arrayNumbersBuses[countBuses] = (bus.numberBus);
                countBuses++;
            }
        }

        // Задание 3 Удаление автобуса из расписания
        public void deleteBus(String NumberBus) {
            int j = 0;
            for (int i = 0; i < countBuses; i++) {
                if (arrayNumbersBuses[i].equals(NumberBus)) {
                    j = i;
                }
            }
            for (int i = j; i < countBuses - 1; i++) {
                arrayBus[i] = arrayBus[i + 1];
                arrayNumbersBuses[i] = arrayNumbersBuses[i + 1];
            }
            countBuses--;
        }

        @Override
        // Задание 1 Вывод расписания на экран
        public String toString() {
            String res = "";
            for (int i = 0; i < countBuses; i++) {
                res = res + arrayBus[i].toString() + "\n";
            }
            return res;
        }

        // Поиск порядкого номера автобуса
        public int searchSequenceNumber(String busNumber) {
            int f = -1;
            for (int i = 0; i < countBuses; i++) {
                if (arrayNumbersBuses[i].equals(busNumber)) {
                    f = i;
                }
            }
            return f;
        }

        // Задание 2 Добовление времени автобуса по его номеру
        public void addTimeBusesArrivals(String busNumber, String time) {
            if (countBuses < arrayBus.length) {
                if (searchSequenceNumber(busNumber) > -1) {
                    boolean f = true;
                    Bus bus = arrayBus[searchSequenceNumber(busNumber)];
                    for(int i = 0; i < bus.countArrivals; i++){
                        if(time.equals(bus.arrayArrival[i].toString())){
                            f = false;
                        }
                    }
                    if(f){
                        bus.addTimeArrivals(time);
                    }
                }
                else {
                    addBusArrivals(busNumber, 15);
                    arrayBus[searchSequenceNumber(busNumber)].addTimeArrivals(time);
                }
            }
        }

        // Задание 4  Удаление времени прихода автобуса
        public void deleteTimeBusesArrivals(String busNumber, String time){
            int sequenceNumber = searchSequenceNumber(busNumber);
            arrayBus[sequenceNumber].deleteTimeArrivals(time);
        }

        // Задание 5 Добавление автобуса с периодическими остановками (по количеству)
        public void addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals){
            addBusArrivals(busNumber,24);
            arrayBus[countBuses-1].addTimeArrivals(startTime);
            for(int i = 0; i < countArrivals-1; i++) {
                arrayBus[countBuses-1].addSumTime(busNumber, periodTime);

            }

        }

        //  Задание 6 Добавление автобуса с периодическими остановками (по времени)
        public void addBusPeriodically(String busNumber, String startTime, String periodTime, String finishTime){
            int countFinishHourTime = (finishTime.charAt(0) - '0')*10 + (finishTime.charAt(1) - '0');
            int countFinishMinutesTime = (finishTime.charAt(3) - '0')*10 + (finishTime.charAt(4) - '0');
            addBusArrivals(busNumber,24);
            String time = startTime;
            arrayBus[countBuses-1].addTimeArrivals(startTime);
            while (Integer.parseInt(time.substring(0,2)) < countFinishHourTime || (Integer.parseInt(time.substring(0,2)) == countFinishHourTime && Integer.parseInt(time.substring(3)) <= countFinishMinutesTime)){
                time = arrayBus[countBuses-1].sumTime(busNumber, periodTime);
                if(Integer.parseInt(time.substring(0,2)) < Integer.parseInt(finishTime.substring(0,2))) {
                    arrayBus[countBuses - 1].addSumTime(busNumber, periodTime);
                }
            }
        }

        // Здание 7 Ожидание автобуса человеком
        public boolean waitingBusPerson(String startTime, String busNumber, int countMinutes){
            int startHour = Integer.parseInt(startTime.substring(0,2));
            int finishHour = startHour;
            int startMinutes = Integer.parseInt(startTime.substring(3));
            int finishMinutes = startMinutes + countMinutes;
            if(finishMinutes >= 60){
                finishHour++;
                finishMinutes -= 60;
            }
            Bus bus = arrayBus[searchSequenceNumber(busNumber)];
            for(int i = 0; i < bus.countArrivals; i++){
                int busTimeArrivalHour = bus.arrayArrival[i].getIntHours();
                int busTimeArrivalMinutes = bus.arrayArrival[i].getIntMinutes();
                if(((startHour == busTimeArrivalHour && startMinutes <= busTimeArrivalMinutes || busTimeArrivalHour == finishHour && busTimeArrivalMinutes <= finishMinutes))){
                    return true;
                }
            }
            return false;
        }

        // Задание 8  Автобусы на отрезке времени (в пределах суток)
        public String timeBusesOnSegmentSut(String startTime, String endTime){
            String res = "";
            int hourStart = Integer.parseInt(startTime.substring(0,2));
            int minStart = Integer.parseInt(startTime.substring(3));
            int hourEnd = Integer.parseInt(endTime.substring(0,2));
            int minEnd = Integer.parseInt(endTime.substring(3));
            for (int i = 0; i < countBuses; i++){
                for (int j = 0; j < arrayBus[i].countArrivals; j++){
                    int hour = arrayBus[i].arrayArrival[j].getIntHours();
                    int min = arrayBus[i].arrayArrival[j].getIntMinutes();
                    if((hourStart < hour || (hour == hourStart && minStart <= min)) && (hourEnd > hour || (hour == hourEnd && minEnd >= min))){
                        res += arrayBus[i].numberBus + " ";
                        break;
                    }
                }
            }
            return res;
        }

        // Задание 9 Автобусы на отрезке времени (через полночь)
        public String timeBusesOnSegmentNotSut(String startTime, String endTime){
            String res = "";
            int hourStart = Integer.parseInt(startTime.substring(0,2));
            int minStart = Integer.parseInt(startTime.substring(3));
            int hourEnd = Integer.parseInt(endTime.substring(0,2));
            int minEnd = Integer.parseInt(endTime.substring(3));
            if(minEnd >= 60){
                hourEnd++;
                minStart -= 60;
            }
            for (int i = 0; i < countBuses; i++){
                for (int j = 0; j < arrayBus[i].countArrivals; j++){
                    int hour = arrayBus[i].arrayArrival[j].getIntHours();
                    int min = arrayBus[i].arrayArrival[j].getIntMinutes();
                    if((hourStart < hour || hour < hourEnd || (hour == hourStart && minStart <= min)) || (hourEnd == hour && minEnd >= min)){
                        res += arrayBus[i].numberBus + " ";
                        break;
                    }
                }
            }
            return res;
        }

        // Задание 10  Первый автобус для человека
        public String firstBusForPerson(String startTime, String[] numberBuses){
            String res = "";
            int startHour = Integer.parseInt(startTime.substring(0,2));
            int startMin = Integer.parseInt(startTime.substring(3));
            int minHour = 24;
            int minMin = 60;
            for(int i = 0; i < numberBuses.length; i++){
                int n = searchSequenceNumber(numberBuses[i]);
                Bus bus = arrayBus[n];
                for (int j = 0; j < bus.countArrivals; j++){
                    int hour = bus.arrayArrival[j].getIntHours();
                    int min = bus.arrayArrival[j].getIntMinutes();
                    if(hour > startHour || hour == startHour && min >= startMin){
                        if(hour - startHour < minHour || hour - startHour < minHour && min - startMin < minMin){
                            res = bus.numberBus;
                            minHour = hour - startHour;
                            minMin = min - startMin;
                        }
                    }
                }
            }
            return res;
        }

    }

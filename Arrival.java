public static class Arrival{

        private final String time;

        // Конструктор времени прибытия
        public Arrival(String time){
            this.time = time;
        }

        // Вывод времени прибытия
        @Override
        public String toString(){
            return time;
        }

        // Получение значения часов в целых числах
        public int getIntHours(){
            int dozHour = (time.charAt(0) - '0')*10;
            int unitHour = (time.charAt(1) - '0');
            return dozHour + unitHour;
        }
        // Получение значения минут в целых числах
        public int getIntMinutes(){
            int dozMinutes = (time.charAt(3) - '0')*10;
            int unitMinutes = (time.charAt(4) - '0');
            return dozMinutes + unitMinutes;
        }

    }

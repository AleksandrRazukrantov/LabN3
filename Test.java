public class Test {
    public static void main(String[] args){

        // Создаем расписание остановки
        Schedule raspis = new Schedule(100);

        // Создаем три автобуса(Добовляем их в расписание)
        Bus bus1 = new Bus("430",24);
        Bus bus2 = new Bus("440",24);
        Bus bus3 = new Bus("210",24);
        Bus bus4 = new Bus("550", 24);
        raspis.addBusArrivals(bus1);
        raspis.addBusArrivals(bus2);
        raspis.addBusArrivals(bus3);

        // Добовляем время для автобусов (Задание 2)
        raspis.addTimeBusesArrivals("430","09:00");
        raspis.addTimeBusesArrivals("430","10:00");
        raspis.addTimeBusesArrivals("430","12:00");
        raspis.addTimeBusesArrivals("430","15:30");
        raspis.addTimeBusesArrivals("430","18:10");
        raspis.addTimeBusesArrivals("430","23:10");
        raspis.addTimeBusesArrivals("440","09:25");
        raspis.addTimeBusesArrivals("440","10:30");
        raspis.addTimeBusesArrivals("440","11:40");
        raspis.addTimeBusesArrivals("550","21:25");
        raspis.addTimeBusesArrivals("550","23:30");
        raspis.addTimeBusesArrivals("550","03:40");
        raspis.addTimeBusesArrivals("440","13:00");
        raspis.addTimeBusesArrivals("440","17:01");
        raspis.addTimeBusesArrivals("440","19:10");
        raspis.addTimeBusesArrivals("210","14:30");

        // Вывод расписания (Задание 1)
        System.out.println(raspis.toString());

        // Удаление автобуса 210 (Задание 3)
        raspis.deleteBus("210");

        // Удаление времени 17:01 из расписания 440 автобуса (Задание 4)
        raspis.deleteTimeBusesArrivals("440","17:01");

        // Вывод расписания (Задание 1)
        System.out.println(raspis.toString());

        // Создаем автобус 340p с переодическими остановками (по количеству) (Задание 5)
        raspis.addBusPeriodically("340p", "08:30","02:30", 4);

        // Создаем автобус 240p с переодическими остановками (по времени) (Задание 6)
        raspis.addBusPeriodically("240p", "10:15", "03:00", "22:00");

        // Вывод расписания (Задание 1)
        System.out.println(raspis.toString());

        // Проверяем дождется ли человек, пришедший в 11:00 и ждавший 12 минут 430 автобуса (Задание 7)
        System.out.println(raspis.waitingBusPerson("11:00", "430", 12));

        // Возвращаем номера автобусов которые проезжают в период с 14:00 до 17:30 (Задание 8)
        System.out.println(raspis.timeBusesOnSegmentSut("14:00", "17:30"));

        // Возвращаем номера автобусов которые проезжают в период с 22:00 до 05:30 (Задание 9)
        System.out.println(raspis.timeBusesOnSegmentNotSut("22:00", "05:30"));

        // Создаем список желаемых автобусов
        String[] list = new String[3];
        list[0] = "440";
        list[1] = "340p";
        list[2] = "550";
        // Какой автобус приедет первый из списка нужных если человек прийдет на остановку в 15:00
        System.out.println(raspis.firstBusForPerson("15:00", list));

    }

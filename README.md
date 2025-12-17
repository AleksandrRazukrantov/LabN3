## Отчет по лабораторной работе № 3

#### № группы: `ПМ-2501`

#### Выполнил: `Разукрантов Александр Евгеньевич`

#### Вариант: `15`

### Cодержание:

- [Постановка задачи](#1-постановка-задачи)
- [Реализация и описание методов](#2-реализация-и-описание-методов)
- [Программа](#3-программа)
- [Анализ правильности решения и формат ввода](#4-анализ-правильности-решения-и-формат-ввода)

### 1. Постановка задачи

> Разработать программу для работы с расписанием автобусов на остановке. Реализовать
> функционал добавления и удаления автобусов, управления временем их прибытия, а
> также обработки запросов, связанных с расписанием.

#### Описание функционала
##### 1. Вывод расписания
Отображение расписания в формате:
НомерАвтобуса1: hh:mm, hh:mm
НомерАвтобуса2: hh:mm, hh:mm
Все моменты времени в расписании упорядочены хронологически.
##### 2. Добавление времени прихода автобуса
Добавление времени прибытияавтобуса поего номеруивременивформате «hh:mm».
Если автобуса с данным номером ещё нет, он добавляется в расписание. Если ука
занное время уже есть, оно не добавляется.
##### 3. Удаление автобуса из расписания
Удаление автобуса с указанным номером из расписания.
##### 4. Удаление времени прихода автобуса
Удаление конкретного времени прибытия автобуса по его номеру и указанному
времени.
##### 5. Добавление автобуса с периодическими остановками (по количеству)
Добавление автобуса, который начинает ходить с указанного времени и делает
остановки через заданный интервал (в минутах). Указывается номер автобуса,
время начала, интервал и количество остановок.
##### 6. Добавление автобуса с периодическими остановками (по времени)
Добавление автобуса, который начинает ходить с указанного времени и делает
остановки через заданный интервал до указанного времени включительно. Ука
зывается номер автобуса, время начала, интервал и конечное время.
##### 7. Ожидание автобуса человеком
Человек приходит на остановку в указанное время и ждёт автобус с переданным
номером определённое количество минут. Определяет, дождётся ли человек авто
буса.
##### 8. Автобусы на отрезке времени (в пределах суток)
Возвращает номера автобусов, которые приезжают на остановку в указанный от
резок времени (без повторений). Гарантируется, что отрезок времени находится в
пределах одних суток.

##### 9. Автобусы на отрезке времени (через полночь)
Возвращает номера автобусов, которые приезжают на остановку в указанный от
резок времени, включая переход через полночь (без повторений).
##### 10. Первый автобус для человека
Человек приходит на остановку в указанное время и садится на первый автобус
из переданного списка номеров. Возвращает номер автобуса, который приедет
первым.

### 2. Реализация и описание методов
Для реализации основных методов я создовал дополнительные классы и методы и сначала я опишу их.
##### Класс Arrival
Класс нужен для хранения времени.

Поля:

time - сторока хрянящая, строку времени в формате «hh:mm».

Конструктор: принимает строку времени в формате «hh:mm» и присваевает её значение в time.
```java
public Arrival(String time){
    this.time = time;
        }
```

Метод toString(): возвращает time.
```java
@Override
public String toString(){
    return time;
}
```

Метод getIntHours(): Так как при работе со временем удобней использовать не символы а числовые значения, то я решил создать отдельный метод, который будкт возвращать количество часов в типи данных int.
```java
public int getIntHours(){
    int dozHour = (time.charAt(0) - '0')*10;
    int unitHour = (time.charAt(1) - '0');
    return dozHour + unitHour;
        }
```

Метод getIntMinutes(): Так как при работе со временем удобней использовать не символы а числовые значения, то я решил создать отдельный метод, который будкт возвращать количество часов в типи данных int.
```java
public int getIntMinutes(){
    int dozMinutes = (time.charAt(3) - '0')*10;
    int unitMinutes = (time.charAt(4) - '0');
    return dozMinutes + unitMinutes;
        }
```

##### Класс Bus
Класс нужен для хранения отдельных автобусов с массивами времени их прибытия.

Поля:

numberBus - хранит номер автобуса в строке.

countArrivals - хранит количество прибытий автобуса на остановку, тип данных int.

arrayArrival - хранит массив времени всех прибытий автобуса на остановку (Для этого и нужен класс Arrival)

Конструктор: принимает номер автобуса в строке и принимает максимально возможное число поездок автобуса в сутках, присваевает номер автобуса полю numberBus, и указывает длинну массива arrayArrival.
```java
public Bus(String numberBus, int maxCountArrivals){
    this.numberBus = numberBus;
    arrayArrival = new Arrival[maxCountArrivals];
        }
```

Метод sortArrivals(): сортирует текущий массив прибытий автобуса в хронологическом порядке. Сортировка методом пузырька.
```java
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
```

Метод addTimeArrivals(String time): принимает строку времени и добовляет её в массив этого автобуса, значению в массиве под номером countArrivals задаеться значение time, и число прибытий увеличиваеться на 1, далее массив сортируеться в хронологическом порядке.
```java
public void addTimeArrivals(String time){
    if(countArrivals < arrayArrival.length){
        arrayArrival[countArrivals] = new Arrival(time);
        countArrivals++;
        sortArrivals();
    }
}
```

Метод deleteTimeArrivals(String time): принимает строку времени и удаляет её из массива этого автобуса, находим номер времени в массиве прибытий, которое нам нужно удалить, далее начиная с этого номера в цикле ствавим на место текущего следующий, таким образом получим массив без времени которое нужно удалить, и уменьшаем количество прибытий на 1, далле сортируем.
```java
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
```

Метод toString(): возвращает все элементы массива прибытий в виде строки.
```java
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
```

Метод sumTime(String time): принимает строку времени и прибавляет это время к последнему времени из массива прибытий (Последний, так как этот метод будет необходим только для реализации переодических автобусов, где время будет строиться от последнего элемента прибавлением периода). Так как время вводиться всегда в формате строки то для начала найдем количество часов и минут, далее прибавим их к текущему времени (время последнего элемента массива прибытий), если в итоге количество минут стало больше или равно 60 то увеличиваем количество часов на 1 и вычитаем 60 из получившегося количества минут. Далее возвращаем итоговое время в виде строки по правилу.
```java
public String sumTime(String time){
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
                    resTime = "0" + hour + ":" + minutes;
                }
                else {
                    resTime = "0" + hour + ":0" + minutes;
                }
            }
            else {
                if(minutes >= 10) {
                    resTime = hour + ":" + minutes;
                }
                else {
                    resTime = hour + ":0" + minutes;
                }
            }
            return resTime;
        }

```

##### Класс Schedule
Основной класс для реализации заданий.

Поля:

countBuses - количество автобусов прибывающих на остановку, изначально 0, тип данных int.

arrayBus - массив всех прибывающих автобусов.

arrayNumbersBuses - массив для всех номеров прибывающих автобусов (где i элемент arrayBus имеет номер i элемента arrayNumbersBuses).

Конструктор: получает максимально возможное количество автобусов maxCountBuses, которое может прийти на остановку. Создает arrayBus и arrayNumbersBuses и задает им длинну maxCountBuses.
```java
public Schedule(int maxCountBuses) {
    arrayBus = new Bus[maxCountBuses];
    arrayNumbersBuses = new String[maxCountBuses];
}
```

Метод addBusArrivals(String numberBus, int maxCountArrivals): получает номер автобуса numberBus и максимальное количество прибытий этого автобуса maxCountArrivals, создается новый автобус он добовляется в массив автобусов (если может) на место с индексом текущего количества автобусов (значение поля countBuses) и затем количество автобусов поле countBuses увеличивается на 1.
```java
public void addBusArrivals(String numberBus, int maxCountArrivals) {
            if (countBuses < arrayBus.length) {
                arrayBus[countBuses] = new Bus(numberBus, maxCountArrivals);
                arrayNumbersBuses[countBuses] = numberBus;
                countBuses++;
            }
        }
```
Метод searchSequenceNumber(String busNumber): получает номер автобуса busNumber и возвращает его индекс (если такого номера нет то возвращает -1) в списке всех автобусов arrayBus, то есть зная номер автобуса с помощью данного метода можно будет узнать сам автобус. Создаем переменную f, в котрой изначально значение -1, далее пробигаем по массиву arrayNumbersBuses и сравниваем элементы массива с busNumber, если нашли то f становиться текущем индексом, если же не нашли то f остается -1, затем возвращаем f.
```java
public int searchSequenceNumber(String busNumber) {
            int f = -1;
            for (int i = 0; i < countBuses; i++) {
                if (arrayNumbersBuses[i].equals(busNumber)) {
                    f = i;
                }
            }
            return f;
        }
```

Итак, все вспомогательные классы и методы были описаны, можно переходить к описанию и реализации самих заданий

Задание 1 Вывод расписания

Метод toString(): возращает строку (строки) всех автобусов с их временем или, проще говоря, само расписание.
```java
@Override
public String toString() {
    String res = "";
    for (int i = 0; i < countBuses; i++) {
        res = res + arrayBus[i].toString() + "\n";
    }
    return res;
}
```

Задание 2 Добовление времени автобуса по его номеру

Метод addTimeBusesArrivals(String busNumber, String time): получает номер автобуса busNumber и время его прибытия time, метод добовляет (если такого автобуса еще не было то добовляет новый автобус) время time в массив прибытий автобуса с номером busNumber. Используя метод searchSequenceNumber(), мы узнаем есть ли автобус этим номером в расписании, для этого необходимо чтобы searchSequenceNumber(busNumber) было больше -1, если верно то проверяем есть ли уже такое время прибытие данного автобуса если есть то заканчиваем метод, если нет то добовляем время с помощью метода addTimeArrivals(), если такого автобуса еще не было то добовляем новый автобус с помощью метода addBusArrivals() и уже к этому автобусу добовляем данное время с помощью метода addTimeArrivals().
```java
public void addTimeBusesArrivals(String busNumber, String time) {
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
```

Задание 3 Удаление автобуса из расписания

Метод deleteBus(String NumberBus): получает номер автобуса  NumberBus, определяет с помощью метода searchSequenceNumber() номере автобуса j, который нужно удалить далле аналогично с удалением времени прибыйтий, сдвигаем начиная с j на один все элементы arrayBus и arrayNumbersBuses и получим, что в  новых массивах не будет j элемента, далее уменьшаем поле countBuses на 1.
```java
public void deleteBus(String NumberBus) {
            int j = searchSequenceNumber(NumberBus);
            for (int i = j; i < countBuses - 1; i++) {
                arrayBus[i] = arrayBus[i + 1];
                arrayNumbersBuses[i] = arrayNumbersBuses[i + 1];
            }
            countBuses--;
        }
```


Задание 4 Удаление времени прихода автобуса

Метод deleteTimeBusesArrivals(String busNumber, String time): получает номер автобуса  NumberBus и  время time, которое нужно удалить, определяет с помощью метода searchSequenceNumber() номере автобуса, в котором нужно удалить время и применяет метод deleteTimeArrivals().
```java
public void deleteTimeBusesArrivals(String busNumber, String time){
            int sequenceNumber = searchSequenceNumber(busNumber);
            arrayBus[sequenceNumber].deleteTimeArrivals(time);
        }
```

Задание 5 Добавление автобуса с периодическими остановками (по количеству)

Метод addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals): получает номер автобуса  NumberBus, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и  количество прибытий countArrivals, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (countArrivals-1) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода).
```java
public void addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals){
            addBusArrivals(busNumber,24);
            arrayBus[countBuses-1].addTimeArrivals(startTime);
            for(int i = 0; i < countArrivals-1; i++) {
                String time = arrayBus[countBuses-1].sumTime(periodTime);
                arrayBus[countBuses-1].addTimeArrivals(time);
            }
        }
```

Задание 6 Добавление автобуса с периодическими остановками (по времени)


Метод addBusPeriodically(String busNumber, String startTime, String periodTime, String finishTime): получает номер автобуса  busNumber, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и конечное время finishTime, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (пока time (изначально startTime ) меньше finishTime, то есть количество часов time меньше чем количество часов finishTime или часы одинаковые, а количество минут time меньше или равно чем количество минут finishTime ) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода) и если новое время по часам меньше finishTime то добовляем его.
```java
public void addBusPeriodically(String busNumber, String startTime, String periodTime, String finishTime){
            int countFinishHourTime = (finishTime.charAt(0) - '0')*10 + (finishTime.charAt(1) - '0');
            int countFinishMinutesTime = (finishTime.charAt(3) - '0')*10 + (finishTime.charAt(4) - '0');
            addBusArrivals(busNumber,24);
            String time = startTime;
            arrayBus[countBuses-1].addTimeArrivals(startTime);
            while (Integer.parseInt(time.substring(0,2)) < countFinishHourTime || (Integer.parseInt(time.substring(0,2)) == countFinishHourTime && Integer.parseInt(time.substring(3)) <= countFinishMinutesTime)){
                time = arrayBus[countBuses-1].sumTime(periodTime);
                if(Integer.parseInt(time.substring(0,2)) < Integer.parseInt(finishTime.substring(0,2))) {
                    arrayBus[countBuses - 1].addTimeArrivals(time);
                }
            }
        }
```

Задание 7 Ожидание автобуса человеком


Метод waitingBusPerson(String startTime, String busNumber, int countMinutes): получает начальное время startTime, номер автобуса  NumberBus и количество минут ожидания countMinutes, метод выдает true или false в зависимости прийдет ли автобус с номером NumberBus за время ожидания или нет. Для этого создаем пременые типа данных int для хранения часов и минут startTime, далее добовляем к минутной части startTime countMinutes и если количество минут становится больше или равно 60 вычитаем из него 60 и увеличиваем количество часов на 1. Далее с помощью цикла пробегаем по всем временам прибытия автобуса и если найдем то, которое лежит между начальным временем и конечным то возвращаем true, иначе по завершении цикла возвращаем false.
```java
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
```

Задание 8 Автобусы на отрезке времени (в пределах суток)


Метод timeBusesOnSegmentSut(String startTime, String endTime): получает начальное время startTime и конечное время промежутка endTime, метод возвращает номера автобусов, которые будут на остановке в данный промежуток времени. Нашли количество часов и минут endTime и startTime в int, чтобы легче писать условие, что какое то время лежит в прмежутке, далее пробегаем по массиву автобусов и в каждом автобусе пробегаем по его времени прибытия, если время прибытия лежит между endTime и startTime то добовляем его в результат (res) и выходим из цилка, если же нет таких прибытий то ничего не добовляем. 
```java
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
```

Задание 9 Автобусы на отрезке времени (через полночь)


Метод timeBusesOnSegmentNotSut(String startTime, String endTime): получает начальное время startTime и конечное время промежутка endTime, метод возвращает номера автобусов, которые будут на остановке в данный промежуток времени. Метод отличается от timeBusesOnSegmentSut() только условием, если в timeBusesOnSegmentSut() требуется чтобы время прибытия было больше или равно времени начала И меньше или равно времени конца то здесь требуется, чтобы время прибытия было больше или равно времени начала ИЛИ меньше или равно времени конца.
```java
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

```

Задание 10 Первый автобус для человека


Метод firstBusForPerson(String startTime, String[] numberBuses): получает время прихода человека на остановку startTime, и массив тех автобусов, которых он ждет, метод возвращает номер автобуса (из списка), который приедет раньше всех к startTime. Перевели часы и минуты startTime в int, далее создали две пременных для нахождения минимума minHour и minMin. Далее пробигаем по numberBuses и пробигаем по временам прибитий элементов numberBuses, если время прибытия больше startTime и разница между startTime и временем прибытия меньше чем minHour и minMin (меньше чем minHour, если равно то меньше minMin) то результату присваивается номер данного автобуса. Возвращается результат.
```java
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
```

### 3. Программа

```java

public class Test {
    public static void main(String[] args){

        // Создаем расписание остановки
        Schedule raspis = new Schedule(100);

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

    // Отдельно создал класс для хранения времени прибытия автобуса
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
    // Отдельно создал класс для автобусов
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

        // Суммирование времени
        public String sumTime(String time){
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
                    resTime = "0" + hour + ":" + minutes;
                }
                else {
                    resTime = "0" + hour + ":0" + minutes;
                }
            }
            else {
                if(minutes >= 10) {
                    resTime = hour + ":" + minutes;
                }
                else {
                    resTime = hour + ":0" + minutes;
                }
            }
            return resTime;
        }
    }

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
                arrayNumbersBuses[countBuses] = numberBus;
                countBuses++;
            }
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

        // Задание 1 Вывод расписания
        @Override
        public String toString() {
            String res = "";
            for (int i = 0; i < countBuses; i++) {
                res = res + arrayBus[i].toString() + "\n";
            }
            return res;
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
        // Задание 3 Удаление автобуса из расписания
        public void deleteBus(String NumberBus) {
            int j = searchSequenceNumber(NumberBus);
            for (int i = j; i < countBuses - 1; i++) {
                arrayBus[i] = arrayBus[i + 1];
                arrayNumbersBuses[i] = arrayNumbersBuses[i + 1];
            }
            countBuses--;
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
                String time = arrayBus[countBuses-1].sumTime(periodTime);
                arrayBus[countBuses-1].addTimeArrivals(time);
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
                time = arrayBus[countBuses-1].sumTime(periodTime);
                if(Integer.parseInt(time.substring(0,2)) < Integer.parseInt(finishTime.substring(0,2))) {
                    arrayBus[countBuses - 1].addTimeArrivals(time);
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

}


```

### 4. Анализ правильности решения и формат ввода

Ввод: номер автобуса можно вводить как угодно (то есть как цифры так и буквы), время нужно вводить в формат hh:mm, причем длинна строки всегда должна быть 5 (то есть если нужно ввести время 9 утра 9 минут его следует записать в виде 09:09)

##### Описание класса Test

Описание будет по блокам

Первый блок реализация и тесты на 1 и 2 задание

```java
public class Test {
    public static void main(String[] args){

        // Создаем расписание остановки
        Schedule raspis = new Schedule(100);

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
}
```

Создали расписание. Добавили четыре автобуса 430, 440, 550, 210, также добавили им время прибытия их на остановку

5. Тест на 1 и 2 задания:

    - **Output**:
        ```
             430 : 09:00, 10:00, 12:00, 15:30, 18:10, 23:10
             440 : 09:25, 10:30, 11:40, 13:00, 17:01, 19:10
             550 : 03:40, 21:25, 23:30
             210 : 14:30
        ```



























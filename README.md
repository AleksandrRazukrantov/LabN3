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


Метод addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals): получает номер автобуса  NumberBus, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и  количество прибытий countArrivals, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (countArrivals-1) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода).
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


Метод addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals): получает номер автобуса  NumberBus, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и  количество прибытий countArrivals, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (countArrivals-1) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода).
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


Метод addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals): получает номер автобуса  NumberBus, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и  количество прибытий countArrivals, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (countArrivals-1) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода).
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


Метод addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals): получает номер автобуса  NumberBus, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и  количество прибытий countArrivals, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (countArrivals-1) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода).
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

Задание 10 Добавление автобуса с периодическими остановками (по количеству)


Метод addBusPeriodically(String busNumber, String startTime, String periodTime, int countArrivals): получает номер автобуса  NumberBus, начальное время startTime, то есть время первого прибытия, период пребытий String periodTime и  количество прибытий countArrivals, метод добовляет в расписания переодический автобус. Создаем новый автобус с помощью метода addBusArrivals(), далее добовляем стартовое время к этому автобусу с помощью метода addTimeArrivals(), далее с помощью цикла добовяем (countArrivals-1) время, где к каждому новому времени применен метод sumTime(periodTime) (то есть прибавление периода).
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


### 4. Анализ правильности решения и формат ввода






























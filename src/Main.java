class AnimalThread extends Thread {
    private String name; // Имя потока
    public int priority; // Приоритет потока
    private int distanceCovered = 0; // Пройденное расстояние

    public AnimalThread(String name, int priority) {
        this.name = name;
        this.priority = priority;
        setPriority(priority); // Установка приоритета потока
    }

    @Override
    public void run() {
        while (distanceCovered < 100) { // Дистанция гонки
            try {
                Thread.sleep(100); // Имитация времени, затраченного на движение
                distanceCovered += (int) (Math.random() * 10); // Увеличение расстояния (от 0 до 9)
                System.out.println(name + " пробежал " + distanceCovered + " метров.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " достиг финиша!");
    }

    public int getDistanceCovered() {
        return distanceCovered;
    }
}

public class Main {
    public static void main(String[] args) {
        AnimalThread rabbit = new AnimalThread("Кролик", Thread.MAX_PRIORITY);
        AnimalThread turtle = new AnimalThread("Черепаха", Thread.MIN_PRIORITY);

        // Запуск потоков
        rabbit.start();
        turtle.start();

        // Динамическое изменение приоритета
        try {
            Thread.sleep(500); // Ждем полсекунды перед изменением приоритета
            if (turtle.getDistanceCovered() < rabbit.getDistanceCovered()) {
                System.out.println("Изменение приоритета: Черепаха догоняет Кролика!");
                turtle.setPriority(Thread.MAX_PRIORITY); // Изменяем приоритет Черепахи
                rabbit.setPriority(Thread.MIN_PRIORITY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ждем завершения потоков
        try {
            rabbit.join();
            turtle.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Гонка закончена!");
    }
}

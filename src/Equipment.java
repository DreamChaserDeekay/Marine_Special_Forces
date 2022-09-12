public class Equipment {
    String name; // 장비 이름
    int hp;
    int mp;
    int attack;
    int defense;
    String description; // 장비 설명

    Equipment(String name, int hp, int mp, int attack, int defense, String description) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.attack = attack;
        this.defense = defense;
        this.description = description;
    }
}

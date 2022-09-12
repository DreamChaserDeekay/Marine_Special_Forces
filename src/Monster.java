public class Monster {
    String name;
    String rank;
    static String[] ranks = new String[] {"5 Tier", "4 Tier", "3 Tier", "2 Tier", "1 Tier"};
    int hp;
    int mp;
    int defense;
    int exp;
    String description;
    Skill skill;

    Monster(String name, String rank, int hp, int mp, int defense, int exp, String description, Skill skill) {
        this.name = name;
        this.rank = rank;
        this.hp = hp;
        this.mp = mp;
        this.defense = defense;
        this.exp = exp;
        this.description = description;
        this.skill = skill;
    }
}

class Skill {
    String name;
    int damage;
    int mp;

    Skill(String name, int damage, int mp) {
        this.name = name;
        this.damage = damage;
        this.mp = mp;
    }
}

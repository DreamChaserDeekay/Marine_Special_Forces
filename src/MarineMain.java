import java.util.Scanner;

class Marine {
    String marineName; // 해병 이름
    // 해병 스탯 (체력, 마나, 공격력, 방어력, 경험치)
    String marineRank; // 해병 계급
    // 해병 체력
    int basicMarineHp; // 기본 체력
    int additionalHp; // 추가 체력
    int totalMarineHp; // 총 체력 (기본 체력 + 추가 체력)
    int currentMarineHp; // 현재 체력
    // 해병 마나
    int basicMarineMp; // 기본 마나
    int additionalMp; // 추가 마나
    int totalMarineMp; // 총 마나 (기본 마나 + 추가 마나)
    int currentMarineMp; // 현재 마나
    // 해병 공격력
    int basicMarineAttack;
    int additionalAttack;
    int totalMarineAttack;
    // 해병 방어력
    int basicMarineDefense;
    int additionalDefense;
    int totalMarineDefense;
    int marineExp; // 해병 경험치
    // 해병 장착 장비 (무기, 머리, 상의, 장갑, 하의, 신발)
    Equipment[] marineEquip = new Equipment[6];
    // 해병 인벤토리
    Equipment[] inventory = new Equipment[5];
    int[] inventoryIdx = new int[] {0, 0, 0, 0, 0};

    // 계급 체계
    static String[] rank = new String[] {"훈련병", "이등병", "일병", "상병", "병장", "???" };
    int rankIdx = 1;
    final int private2ndExp = 25; // 이등병 진급 경험치
    final int private1stExp = 75; // 일병 진급 경험치
    final int corporal = 150; // 상병 진급 경험치
    final int sergeant = 250; // 병장 진급 경험치

    final int unknown = 1000000000;
    int nextRankExp = private2ndExp; // 최초 진급 필요 경험치는 이등병 진급 경험치

    private Scanner input = new Scanner(System.in);

    Marine(String marineName, String marineRank, int basicMarineHp, int basicMarineMp, int basicMarineAttack, int basicMarineDefense, int marineExp) { // 해병 생성자
        // 객체 필드값 초기화 (경험치는 0)
        this.marineName = marineName;
        this.marineRank = marineRank;
        this.basicMarineHp = basicMarineHp;
        this.basicMarineMp = basicMarineMp;
        this.basicMarineAttack = basicMarineAttack;
        this.basicMarineDefense = basicMarineDefense;
        this.marineExp = marineExp;
    }

    // 메인 메뉴
    public int mainMenu() {
        while (true) {
            System.out.println("-".repeat(16) + "해병 키우기 V1" + "-".repeat(16));
            System.out.println(1 + ". 해병 메뉴");
            System.out.println(2 + ". 도감 메뉴");
            System.out.println(3 + ". 몬스터 대전");
            System.out.println(4 + ". 게임 종료");
            System.out.print("[안내] 선택하실 메뉴의 번호를 입력하세요 : ");
            int menuNum = input.nextInt();
            if (menuNum < 1 || menuNum > 4) {
                System.out.println("[안내] 목록에 없는 번호입니다.");
            } else return menuNum;
        }
    }
    // 해병 관련 메뉴
    public int marineMenu() {
        int menuNum;
        while (true) {
            System.out.println("-".repeat(45));
            System.out.println("[안내] 해병 관련 메뉴입니다.");
            System.out.println(1 + ". 스탯 확인");
            System.out.println(2 + ". 장비 확인");
            System.out.println(3 + ". 장비 장착");
            System.out.println(4 + ". 인벤토리 확인");
            System.out.println(5 + ". 휴식");
            System.out.printf("[안내] 선택하실 메뉴의 번호를 입력하세요 (메인 메뉴로 나가시려면 0을 입력하세요) : \n");
            menuNum = input.nextInt();
            if (menuNum > 5) {
                System.out.println("[안내] 목록에 없는 번호입니다.");
            } else if (menuNum == 0) break;
            else return menuNum;
        }
        return menuNum;
    }
    // 도감 관련 메뉴
    public int dictionaryMenu() {
        int dicNum;
        while (true) {
            System.out.println("-".repeat(45));
            System.out.println("[안내] 도감 관련 메뉴입니다.");
            System.out.println(1 + ". 장비 도감");
            System.out.println(2 + ". 몬스터 도감");
            System.out.printf("[안내] 선택하실 메뉴의 번호를 입력하세요 (메인 메뉴로 나가시려면 0을 입력하세요) : \n");
            dicNum = input.nextInt();
            if (dicNum > 2) {
                System.out.println("[안내] 목록에 없는 번호입니다.");
            } else if (dicNum == 0) break;
            else return dicNum;
        }
        return dicNum;
    }
    // 계급 상승
    void rankUp() {
        if (marineExp >= private2ndExp && marineExp < private1stExp && marineRank == rank[0]) {
            System.out.printf("[안내] 축하합니다! %s %s은 %s로 진급했습니다!\n", marineName, marineRank, rank[1]);
            rankIdx++;
            marineRank = rank[1]; // 해병 현 계급 이등병으로 진급
            nextRankExp = private1stExp;
            // 스탯 증가
            basicMarineHp += 10;
            basicMarineMp += 10;
            basicMarineAttack += 1;
            basicMarineDefense += 1;

            statUpdate();
            System.out.printf("[안내] %s %s의 스탯이 증가했습니다. '해병 스탯 확인' 메뉴에서 확인해보세요!\n", marineName, marineRank);

            equipToInventory(K2);
            System.out.printf("[안내] 보상으로 새로운 장비 %s를 지급했습니다. 인벤토리를 확인해보세요!\n", K2.name);

            recover();
            System.out.println("[안내] 체력과 마나가 모두 회복되었습니다!");
        } else if (marineExp >= private1stExp && marineExp < corporal && marineRank == rank[1]) {
            System.out.printf("[안내] 축하합니다! %s %s은 %s로 진급했습니다!\n", marineName, marineRank, rank[2]);
            rankIdx++;
            marineRank = rank[2];
            nextRankExp = corporal;
            // 스탯 증가
            basicMarineHp += 15;
            basicMarineMp += 15;
            basicMarineAttack += 2;
            basicMarineDefense += 1;

            statUpdate();
            System.out.printf("[안내] %s %s의 스탯이 증가했습니다. '해병 스탯 확인' 메뉴에서 확인해보세요!\n", marineName, marineRank);

            equipToInventory(newCombatSuit);
            equipToInventory(newCombatPants);
            System.out.printf("[안내] 보상으로 새로운 장비 %s와 %s를 지급했습니다. 인벤토리를 확인해보세요!\n", newCombatSuit.name, newCombatPants.name);

            recover();
            System.out.println("[안내] 체력과 마나가 모두 회복되었습니다!");
        } else if (marineExp >= corporal && marineExp < sergeant && marineRank == rank[2]) {
            System.out.printf("[안내] 축하합니다! %s %s은 %s로 진급했습니다!\n", marineName, marineRank, rank[3]);
            rankIdx++;
            marineRank = rank[3];
            nextRankExp = sergeant;
            // 스탯 증가
            basicMarineHp += 20;
            basicMarineMp += 20;
            basicMarineAttack += 3;
            basicMarineDefense += 1;
            statUpdate();
            System.out.printf("[안내] %s %s의 스탯이 증가했습니다. '해병 스탯 확인' 메뉴에서 확인해보세요!\n", marineName, marineRank);

            equipToInventory(leatherGloves);
            equipToInventory(helmet);
            equipToInventory(newWalker);
            System.out.printf("[안내] 보상으로 새로운 장비 %s, %s, %s를 지급했습니다. 인벤토리를 확인해보세요!\n", leatherGloves.name, helmet.name, newWalker.name);

            recover();
            System.out.println("[안내] 체력과 마나가 모두 회복되었습니다!");
        } else if (marineExp >= sergeant && marineExp < unknown && marineRank == rank[3]) {
            System.out.printf("[안내] 축하합니다! %s %s은 %s로 진급했습니다!\n", marineName, marineRank, rank[4]);
            rankIdx++;
            marineRank = rank[4];
            nextRankExp = unknown;
            // 스탯 증가
            basicMarineHp += 30;
            basicMarineMp += 30;
            basicMarineAttack += 5;
            basicMarineDefense += 2;
            statUpdate();
            System.out.printf("[안내] %s %s의 스탯이 증가했습니다. '해병 스탯 확인' 메뉴에서 확인해보세요!\n", marineName, marineRank);
            recover();
            System.out.println("[안내] 체력과 마나가 모두 회복되었습니다!");
        }
    }
    // 기본 장비 장착
    void equipBasic() {
        marineEquip[0] = m16;
        marineEquip[1] = beret;
        marineEquip[2] = oldCombatSuit;
        marineEquip[3] = oldGloves;
        marineEquip[4] = oldCombatPants;
        marineEquip[5] = oldWalker;

        equipStatUp(); // 장착 장비 스탯 반영
    }
    // 아이템을 인벤토리로 옮기기
    void equipToInventory(Equipment equipment) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = equipment;
                inventoryIdx[i]++;
                break;
            } else if (equipment.name.equals(inventory[i].name) && inventory[i] != null) {
                inventoryIdx[i]++;
                break;
            } else continue;
        }
    }
    // 인벤토리에 있는 아이템을 해병에 장착
    void inventoryToEquip() {
        int equipNum;
        while (true) {
            System.out.printf("[안내] 장착할 장비의 번호를 입력하세요 (메인 메뉴로 나가시려면 0을 입력하세요) : ");
            equipNum = input.nextInt();
            if (equipNum == 0) {
                break;
            } else if (inventory[equipNum - 1] == null) {
                System.out.println("[안내] 해당 슬롯은 비어있습니다.");
            } else if (equipNum > inventory.length) {
                System.out.println("[안내] 목록에 없는 번호입니다.");
            } else {
                equipCheck();
                System.out.printf("[안내] 해당 장비를 장착할 슬롯 번호를 입력하세요 : \n");
                int slotNum = input.nextInt();

                currentMarineHp += inventory[equipNum - 1].hp - marineEquip[slotNum - 1].hp;
                currentMarineMp += inventory[equipNum - 1].mp - marineEquip[slotNum - 1].mp;

                marineEquip[slotNum - 1] = inventory[equipNum - 1];
                inventoryIdx[equipNum - 1]--;
                inventory[equipNum - 1] = null;

                System.out.println("[안내] 장비 장착이 완료되었습니다. 장비 확인 메뉴에서 확인하세요!");
                break;
            }
        }
    }
    // 장착 장비 스탯 반영
    void equipStatUp() {
        int additionalStatHp = 0;
        int additionalStatMp = 0;
        int additionalStatAttack = 0;
        int additionalStatDefense = 0;
        for (int i = 0; i < marineEquip.length; i++) {
            additionalStatHp += marineEquip[i].hp;
            additionalStatMp += marineEquip[i].mp;
            additionalStatAttack += marineEquip[i].attack;
            additionalStatDefense += marineEquip[i].defense;
        }
        additionalHp = additionalStatHp;
        additionalMp = additionalStatMp;
        additionalAttack = additionalStatAttack;
        additionalDefense = additionalStatDefense;

        statUpdate();
    }
    // 스탯 최신화
    private void statUpdate() {
        totalMarineHp = basicMarineHp + additionalHp;
        totalMarineMp = basicMarineMp + additionalMp;
        totalMarineAttack = basicMarineAttack + additionalAttack;
        totalMarineDefense = basicMarineDefense + additionalDefense;
    }
    // 해병 스탯 확인
    void statCheck() {

        System.out.println("-".repeat(45));
        System.out.printf("[안내] %s 해병의 스탯을 확인합니다.\n", marineName);
        System.out.printf("<현 계급> : %s\n", marineRank);
        System.out.printf("<체력> : %d / %d ([기본] %d + [추가] %d)\n", currentMarineHp, totalMarineHp, basicMarineHp, additionalHp);
        System.out.printf("<마나> : %d / %d ([기본] %d + [추가] %d)\n", currentMarineMp, totalMarineMp, basicMarineMp, additionalMp);
        System.out.printf("<공격력> : %d ([기본] %d + [추가] %d)\n", totalMarineAttack, basicMarineAttack, additionalAttack);
        System.out.printf("<방어력> : %d ([기본] %d + [추가] %d)\n", totalMarineDefense, basicMarineDefense, additionalDefense);
        System.out.printf("<경험치> : %d / %d (다음 계급 : %s)\n", marineExp, nextRankExp, rank[rankIdx]);
    }
    // 해병 장비 확인
    void equipCheck() {
        System.out.println("-".repeat(45));
        System.out.printf("[안내] %s 해병의 장비를 확인합니다.\n", marineName);
        System.out.println(1 + ". <무기> : " + marineEquip[0].name);
        System.out.println(2 + ". <머리> : " + marineEquip[1].name);
        System.out.println(3 + ". <상의> : " + marineEquip[2].name);
        System.out.println(4 + ". <장갑> : " + marineEquip[3].name);
        System.out.println(5 + ". <하의> : " + marineEquip[4].name);
        System.out.println(6 + ". <신발> : " + marineEquip[5].name);
    }
    // 해병 인벤토리 확인
    void inventoryCheck() {
        System.out.println("-".repeat(45));
        System.out.printf("[안내] %s 해병의 인벤토리를 확인합니다.\n", marineName);
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null && inventoryIdx[i] == 0) {
                System.out.println((i + 1) + ". <빈 슬롯>");
            } else System.out.println((i + 1) + ". " + inventory[i].name + " (" + inventoryIdx[i] + " 개)");
        }
    }
    // 장비 검색 메뉴
    int equipSearchMenu() {
        System.out.println("-".repeat(45));
        System.out.println("[안내] 장비를 검색합니다.");
        System.out.println(1 + ". <무기>");
        System.out.println(2 + ". <머리>");
        System.out.println(3 + ". <상의>");
        System.out.println(4 + ". <장갑>");
        System.out.println(5 + ". <하의>");
        System.out.println(6 + ". <신발>");

        int equipNum;
        while (true) {
            System.out.print("[안내] 원하는 부위의 번호를 입력하세요 : ");
            equipNum = input.nextInt();
            if (equipNum < 1 || equipNum > 6) {
                System.out.println("[안내] 목록에 없는 번호입니다.");
            } else return equipNum;
        }
    }
    // 장비 확인 메뉴
    void equipSearch(int equipNum) {
        Outer : while (true) {
            System.out.println("-".repeat(18) + "장비 리스트" + "-".repeat(18));
            for (int i = 0; i < allEquipments[equipNum - 1].length; i++) {
                System.out.println((i + 1) + ". " + allEquipments[equipNum - 1][i].name);
            }
            System.out.print("[안내] 열람할 장비의 번호를 입력하세요 (메인 메뉴로 나가시려면 0을 입력하세요) : ");
            int headNum = input.nextInt();

            for (int j = 0; j < allEquipments[equipNum - 1].length; j++) {
                if (headNum == j + 1) {
                    System.out.println("-".repeat(45));
                    System.out.println("<장비명> : " + allEquipments[equipNum - 1][j].name);
                    System.out.println("<추가 체력> : " + allEquipments[equipNum - 1][j].hp);
                    System.out.println("<추가 마나> : " + allEquipments[equipNum - 1][j].mp);
                    System.out.println("<추가 공격력> : " + allEquipments[equipNum - 1][j].attack);
                    System.out.println("<추가 방어력> : " + allEquipments[equipNum - 1][j].defense);
                    System.out.println("<장비 설명> : " + allEquipments[equipNum - 1][j].description);
                    System.out.println("-".repeat(45));
                    break;
                } else if (headNum == 0) break Outer;
                else if (headNum > allEquipments[equipNum - 1].length) {
                    System.out.println("[안내] 목록에 없는 번호입니다.");
                    break;
                } else continue;
            }
        }
    }
    // 몬스터 검색 메뉴
    int monsterSearchMenu() {
        System.out.println(1 + ". <1 Tier>");
        System.out.println(2 + ". <2 Tier>");
        System.out.println(3 + ". <3 Tier>");
        System.out.println(4 + ". <4 Tier>");
        System.out.println(5 + ". <5 Tier>");

        int monsterNum;
        while (true) {
            System.out.print("[안내] 원하는 티어의 번호를 입력하세요 : ");
            monsterNum = input.nextInt();
            if (monsterNum < 1 || monsterNum > 5) {
                System.out.println("[안내] 목록에 없는 번호입니다.");
            } else return monsterNum;
        }
    }
    // 몬스터 확인 메뉴
    void monsterSearch(int monsterNum) {
        Outer : while (true) {
            System.out.println("-".repeat(17) + "몬스터 리스트" + "-".repeat(17));
            for (int i = 0; i < allMonster[monsterNum - 1].length; i++) {
                System.out.println((i + 1) + ". " + allMonster[monsterNum - 1][i].name);
            }
            System.out.print("[안내] 열람할 몬스터의 번호를 입력하세요 (메인 메뉴로 나가시려면 0을 입력하세요) : ");
            int headNum = input.nextInt();

            for (int j = 0; j < allMonster[monsterNum - 1].length; j++) {
                if (headNum == j + 1) {
                    Monster thisMonster = allMonster[monsterNum - 1][j];
                    System.out.println("-".repeat(45));
                    System.out.println("<몬스터 명> : " + thisMonster.name);
                    System.out.println("<체력> : " + thisMonster.hp);
                    System.out.println("<마나> : " + thisMonster.mp);
                    System.out.printf("<보유 스킬> : %s (데미지 : %d)\n", thisMonster.skill.name, thisMonster.skill.damage);
                    System.out.println("<방어력> : " + thisMonster.defense);
                    System.out.println("<경험치> : " + thisMonster.exp);
                    System.out.println("<몬스터 설명> : " + thisMonster.description);
                    break;
                } else if (headNum == 0) break Outer;
                else if (headNum > allMonster[monsterNum - 1].length) {
                    System.out.println("[안내] 목록에 없는 번호입니다.");
                    break;
                } else continue;
            }
        }
    }
    // 몬스터 대전
    public void monsterBattle(int monsterNum) {
        Outer:
        while (true) {
            System.out.println("-".repeat(17) + "몬스터 리스트" + "-".repeat(17));
            for (int i = 0; i < allMonster[monsterNum - 1].length; i++) {
                System.out.println((i + 1) + ". " + allMonster[monsterNum - 1][i].name);
            }
            System.out.print("[안내] 전투할 몬스터의 번호를 입력하세요 (메인 메뉴로 나가시려면 0을 입력하세요) : ");
            int headNum = input.nextInt();
            
            for (int j = 0; j < allMonster[monsterNum - 1].length; j++) {
                if (headNum == j + 1) {
                    Monster thisMonster = allMonster[monsterNum - 1][j];
                    System.out.printf("[안내] %s 몬스터와의 전투를 시작합니다!\n", thisMonster.name);
                    System.out.println("<몬스터 명> : " + thisMonster.name);
                    System.out.println("<체력> : " + thisMonster.hp);
                    System.out.println("<마나> : " + thisMonster.mp);
                    System.out.printf("<보유 스킬> : %s (데미지 : %d)\n", thisMonster.skill.name, thisMonster.skill.damage);
                    System.out.println("<방어력> : " + thisMonster.defense);
                    System.out.println("<경험치> : " + thisMonster.exp);
                    int currentMonsterHp = thisMonster.hp; // 현재 몬스터 체력
                    int hitMonsterHp; // 피격 후 몬스터 체력
                    int marineBattleExp = marineExp;
                    int hitMarineHp; // 피격 후 해병 체력

                    while (currentMarineHp > 0 && currentMonsterHp > 0) { // 현재 해병 체력 및 몬스터 체력이 0보다 크면
                        System.out.println("-".repeat(45));
                        System.out.printf("[전투] %s 해병의 공격 차례입니다.\n", marineName);
                        System.out.printf("[전투] %s 해병의 <사격>!\n", marineName);
                        System.out.printf("[전투] %s 해병의 공격이 명중했습니다! (공격력 : %d)\n", marineName, totalMarineAttack);
                        if (totalMarineAttack >= currentMonsterHp + thisMonster.defense) { // 해병 공격력이 (방어력 감안) 현재 몬스터 체력보다 크거나 같다면
                            System.out.printf("[전투] %s 몬스터가 쓰러졌습니다!\n", thisMonster.name);
                            marineExp += thisMonster.exp; // 해병 경험치 증가
                            System.out.printf("[안내] 경험치가 %d 만큼 증가했습니다. (%s -> %s)\n", thisMonster.exp, marineBattleExp, marineExp);
                            System.out.println("[안내] 전투를 종료합니다.");
                            break;
                        } else {
                            if (totalMarineAttack <= thisMonster.defense) { // 몬스터 방어력이 해병 공격력보다 높은 경우
                                System.out.println("[전투] 상대 몬스터의 방어력이 너무 높습니다...");
                                hitMonsterHp = currentMonsterHp; // 피격 전과 후 체력이 같다
                                System.out.printf("[전투] %s 몬스터 체력 : (%d -> %d)\n", thisMonster.name, currentMonsterHp, hitMonsterHp);
                                currentMonsterHp = hitMonsterHp; // 몬스터 피격 후 체력을 피격 전 체력으로 할당
                            } else { // 몬스터 방어력보다 해병 공격력이 높은 경우
                                hitMonsterHp = currentMonsterHp - totalMarineAttack + thisMonster.defense; // 몬스터 피격 후 체력
                                System.out.printf("[전투] %s 몬스터 체력 : (%d -> %d)\n", thisMonster.name, currentMonsterHp, hitMonsterHp);
                                currentMonsterHp = hitMonsterHp; // 몬스터 피격 후 체력을 피격 전 체력으로 할당
                            }
                            System.out.println("-".repeat(45));
                            System.out.printf("[전투] %s 몬스터의 공격 차례입니다.\n", thisMonster.name);
                            System.out.printf("[전투] %s 몬스터의 <%s>!\n", thisMonster.name, thisMonster.skill.name);
                            System.out.printf("[전투] %s 몬스터의 공격이 명중했습니다! (공격력 : %d)\n", thisMonster.name, thisMonster.skill.damage);
                            if (thisMonster.skill.damage >= currentMarineHp + totalMarineDefense) {
                                currentMarineHp = 0;
                                System.out.printf("[전투] %s 해병이 쓰러졌습니다!\n", marineName);
                                System.out.printf("[안내] %s 해병은 더 이상 일어설 수 없습니다...\n", marineName);
                                System.out.println("[안내] 메인 메뉴로 돌아갑니다.");
                                break;
                            } else {
                                hitMarineHp = currentMarineHp - thisMonster.skill.damage + totalMarineDefense;
                                System.out.printf("[전투] %s 해병 체력 : (%d -> %d)\n", marineName, currentMarineHp, hitMarineHp);
                                currentMarineHp = hitMarineHp;
                            }
                        }
                    }
                } else if (headNum == 0) break Outer;
                else if (headNum > allMonster[monsterNum - 1].length) {
                    System.out.println("[안내] 목록에 없는 번호입니다.");
                    break;
                } else continue;
            } break;
        }
    }
    // 피 회복
    private void recover() {
        currentMarineHp = totalMarineHp;
        currentMarineMp = totalMarineMp;
    }
    // 해병 휴식
    void takeRest() {
        System.out.printf("[안내] %s %s은 고된 일과를 끝마치고 꿀맛 같은 휴식을 취했습니다...\n", marineName, marineRank);
        recover();
        System.out.println("[안내] 체력과 마나가 모두 회복되었습니다!");

    }
    // 스레드 지연
    void timeDelay(int second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }

    /**
     * 장비 모음
     */
    // 무기
    Equipment m16 = new Equipment("M16", 0, 0, 5, 0,
            "기본으로 지급되는 소총. 총기 수입도 제대로 안 된 것 같아 보인다.");
    Equipment K2 = new Equipment("K2", 0, 0, 8, 0,
            "어디서 많이 보던 소총. 열심히 몬스터 대전을 하다보면 얻을 수 있을지도...?");
    // 머리
    Equipment beret = new Equipment("베레모", 5, 0, 0, 0,
            "기본으로 지급되는 베레모. 제대로 된 방어능력은 없지 않을까?");
    Equipment helmet = new Equipment("방탄모", 5, 0, 0, 1,
            "방어 성능은 확실한 철모. 누가 사용했는지 턱끈이 닳아있다.");
    // 상의
    Equipment oldCombatSuit = new Equipment("구형 전투복(상의)", 10, 0, 0, 0,
            "기본으로 지급되는 전투복 상의. 익숙한 개구리 문양이 눈에 띈다.");
    Equipment newCombatSuit = new Equipment("신형 전투복(상의)", 15, 0, 0, 0,
            "디지털 무늬가 인상적인 전투복 상의. 구형 전투복 보다는 빳빳하다.");
    // 장갑
    Equipment oldGloves = new Equipment("노가다용 목장갑", 5, 0, 0, 0,
            "기본으로 지급되는 목장갑. 여기저기 헤진 부분이 많이 보이는군.");
    Equipment leatherGloves = new Equipment("가죽 장갑", 8, 0, 0, 0,
            "검정색 장갑. 음료 딸 때 사용하면 유용할 듯 하다.");
    // 하의
    Equipment oldCombatPants = new Equipment("구형 전투복(하의)", 5, 0, 0, 0,
            "기본으로 지급되는 전투복 하의. 익숙한 개구리 문양이 눈에 띈다.");
    Equipment newCombatPants = new Equipment("신형 전투복(하의)", 10, 0, 0, 0,
            "디지털 무늬가 인상적인 전투복 하의. 구형 전투복 보다는 빳빳하다.");
    // 신발
    Equipment oldWalker = new Equipment("구형 전투화", 0, 0, 0, 1,
            "기본으로 지급되는 전투화. 손질을 안 한지 꽤 된 것 같다.");
    Equipment newWalker = new Equipment("신형 전투화", 5, 0, 0, 1,
            "편안한 착용감의 전투화. 디지털 무늬가 중간중간 박혀있다.");

    /**
     * 부품 모음
     */
    // 모든 장비 리스트
    Equipment[][] allEquipments = new Equipment[][] {
            {m16, K2}, // 무기
            {beret, helmet}, // 머리
            {oldCombatSuit, newCombatSuit}, // 상의
            {oldGloves, leatherGloves}, // 장갑
            {oldCombatPants, newCombatPants}, // 하의
            {oldWalker, newWalker} // 신발
    };

    /**
     * 몬스터 스킬 모음
     */
    Skill claw = new Skill("할퀴기",5, 0);
    Skill torch = new Skill("용접", 5, 0);
    Skill spine = new Skill("독침", 10, 0);
    Skill psiBlade = new Skill("사이오닉 블레이드", 12, 0);
    Skill moonGlave = new Skill("문글레이브", 15, 0);
    Skill powerGlave = new Skill("파워 글레이브", 20, 0);
    Skill spineTentacle = new Skill("가시촉수", 25, 0);
    Skill kaiserBlade = new Skill("카이저 블레이드", 50, 0);

    /**
     * 몬스터 모음
     */
    Monster zergling = new Monster("저글링", Monster.ranks[0], 35, 0, 0, 5,
            "가장 약한 몬스터. 발톱에 당하면 조금은 아플지도 ...?", claw);
    Monster probe = new Monster("프로브", Monster.ranks[0], 30, 0, 0, 4,
            "익숙하게 생긴 로봇. 용접 하듯이 공격하는 것 같다.", torch);
    Monster hydra = new Monster("히드라", Monster.ranks[1], 80, 0, 0, 10,
            "조금 강한 몬스터. 상대하려면 더러워질 각오 정도는 되어 있어야 한다.", spine);
    Monster zealot = new Monster("질럿", Monster.ranks[1], 60, 0, 1, 12,
            "외계 전사. 말을 걸면 대뜸 칼을 들고 싸움을 걸어오니 조심하자.", psiBlade);
    Monster mutalisk = new Monster("뮤탈리스크", Monster.ranks[2], 120, 0, 1, 20,
            "까다로운 몬스터. 속도가 빨라 좀처럼 명중시키기 어렵다.", moonGlave);
    Monster guardian = new Monster("가디언", Monster.ranks[2], 150, 0, 1, 30,
            "뮤탈리스크의 진화형. 공격 속도는 느리지만 한 방 한 방이 강력하다.", powerGlave);
    Monster lurker = new Monster("러커", Monster.ranks[3], 180, 0, 2, 40,
            "매우 까다로운 몬스터. 체력도 많고 공격력도 강력하다.", spineTentacle);
    Monster ultralisk = new Monster("울트라리스크", Monster.ranks[4], 400, 0, 10, 100,
            "그 어떤 해병도 쓰러트리지 못한 전설의 몬스터. 잡을 수나 있는건가 ...?", kaiserBlade);

    Monster[][] allMonster = new Monster[][] {
            {ultralisk}, // 1티어
            {lurker}, // 2티어
            {mutalisk, guardian}, // 3티어
            {hydra, zealot}, // 4티어
            {zergling, probe} // 5티어
    };

} // Marine 클래스 종료

public class MarineMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("-".repeat(15) + "해병 키우기 V1" + "-".repeat(15));
        System.out.println("[안내] 새로운 해병을 생성합니다.");
        System.out.print("[안내] 해병의 이름을 지어주세요! : ");
        Scanner inputMain = new Scanner(System.in);
        String name = inputMain.nextLine();
        Marine marine = new Marine(name, Marine.rank[0], 30, 0, 1, 0, 0);
        System.out.printf("[안내] %s 해병이 생성되었습니다! (현 계급 : %s)\n", marine.marineName, marine.marineRank);
        
        System.out.println("[안내] 기본 장비를 보급하는 중...");
        marine.equipBasic(); // stat up 포함
        System.out.println("[안내] 기본 장비 보급이 완료되었습니다!");
        

        marine.currentMarineHp = marine.totalMarineHp;
        marine.currentMarineMp = marine.totalMarineMp;

        Main: while (true) {
            int menuNum = marine.mainMenu();

            switch (menuNum) {
                case 1: // 해병 메뉴
                    int num1 = marine.marineMenu();

                    switch (num1) {
                        case 1: // 스탯 확인
                            marine.statCheck();
                            break;
                        case 2: // 장비 확인
                            marine.equipCheck();
                            break;
                        case 3: // 장비 장착
                            marine.inventoryCheck();
                            marine.inventoryToEquip();
                            marine.equipStatUp();
                            break;
                        case 4: // 인벤토리 확인
                            marine.inventoryCheck();
                            break;
                        case 5: // 휴식
                            System.out.println("-".repeat(45));
                            marine.takeRest();
                            break;
                    }

                    break;

                case 2: // 도감 메뉴
                    int num2 = marine.dictionaryMenu();

                    switch (num2) {
                        case 1: // 장비 도감
                            int equipNum = marine.equipSearchMenu();
                            marine.equipSearch(equipNum);
                            break;
                        case 2: // 몬스터 도감
                            System.out.println("-".repeat(45));
                            System.out.println("[안내] 몬스터를 검색합니다.");
                            int monsterNum1 = marine.monsterSearchMenu();
                            marine.monsterSearch(monsterNum1);
                            break;
                    }

                    break;

                case 3: // 몬스터 대전
                    System.out.println("-".repeat(45));
                    System.out.println("[안내] 대전할 몬스터를 검색합니다.");
                    int monsterNum2 = marine.monsterSearchMenu();
                    marine.monsterBattle(monsterNum2);
                    marine.rankUp();
                    break;
                case 4: // 게임 종료
                    System.out.println("[안내] 게임을 종료합니다.");
                    break Main;
            }
        }
    }
}

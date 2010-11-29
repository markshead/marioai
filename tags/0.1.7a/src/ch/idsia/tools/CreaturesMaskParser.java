package ch.idsia.tools;

import ch.idsia.benchmark.mario.engine.sprites.Sprite;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey@idsia.ch
 * Date: Aug 28, 2010
 * Time: 2:36:36 AM
 */

/*
From left to right:
    0)goomba
    1)green koopa
    2)red koopa
    3)spiky
    4)winged goomba
    5)winged green koopa
    6)winged red koopa
    7)winged spiky
    8)spiky flower
*/

public class CreaturesMaskParser
{
    final private static int totalCreaturesCount = 9;

    private static boolean[] creatures;
    public static final int GOOMBA = 0;
    public static final int GREEN_KOOPA = 1;
    public static final int RED_KOOPA = 2;
    public static final int SPIKY = 3;
    public static final int WINGED_GOOMBA = 4;
    public static final int WINGED_GREEN_KOOPA = 5;
    public static final int WINGED_RED_KOOPA = 6;
    public static final int WINGED_SPIKY = 7;
    public static final int SPIKY_FLOWER = 8;

    private static boolean complete = true;
    private static boolean canAdd = true; //true if at least one creature enabled

    public CreaturesMaskParser(String creatures)
    {
        this.creatures = new boolean[totalCreaturesCount];
        while (creatures.length() < totalCreaturesCount)
        {
            creatures = "0" + creatures;
        }

        if (creatures.substring (0, 7).equals ("11111111"))
        {
            complete = true;
        }
        if (creatures.substring (0, 7).equals ("00000000"))
        {
            canAdd = false;
        }

        //TODO: turn to a cycle

        if (creatures.charAt(GOOMBA) == '1') {this.creatures[GOOMBA] = true;}
        if (creatures.charAt(GREEN_KOOPA) == '1') {this.creatures[GREEN_KOOPA] = true;}
        if (creatures.charAt(RED_KOOPA) == '1') {this.creatures[RED_KOOPA] = true;}
        if (creatures.charAt(SPIKY) == '1') {this.creatures[SPIKY] = true;}
        if (creatures.charAt(WINGED_GOOMBA) == '1') {this.creatures[WINGED_GOOMBA] = true;}
        if (creatures.charAt(WINGED_GREEN_KOOPA) == '1') {this.creatures[WINGED_GREEN_KOOPA] = true;}
        if (creatures.charAt(WINGED_RED_KOOPA) == '1') {this.creatures[WINGED_RED_KOOPA] = true;}
        if (creatures.charAt(WINGED_SPIKY) == '1') {this.creatures[WINGED_SPIKY] = true;}
        if (creatures.charAt(SPIKY_FLOWER) == '1') {this.creatures[SPIKY_FLOWER] = true;}
    }

    public static boolean isEnabled(int type)
    {
        return creatures[type];
    }

    public static boolean isComplete()
    {
        return complete;
    }

    public static boolean canAdd()
    {
        return canAdd;
    }

    public static int getNativeType(int num)
    {
        switch (num)
        {
            case GOOMBA:
                return Sprite.KIND_GOOMBA;
            case GREEN_KOOPA:
                return Sprite.KIND_GREEN_KOOPA;
            case RED_KOOPA:
                return Sprite.KIND_RED_KOOPA;
            case SPIKY:
                return Sprite.KIND_SPIKY;
            case WINGED_GOOMBA:
                return Sprite.KIND_GOOMBA_WINGED;
            case WINGED_GREEN_KOOPA:
                return Sprite.KIND_GREEN_KOOPA_WINGED;
            case WINGED_RED_KOOPA:
                return Sprite.KIND_RED_KOOPA_WINGED;
            case WINGED_SPIKY:
                return Sprite.KIND_SPIKY_WINGED;
            case SPIKY_FLOWER:
                return Sprite.KIND_ENEMY_FLOWER;
        }
        return Sprite.KIND_UNDEF;
    }
}
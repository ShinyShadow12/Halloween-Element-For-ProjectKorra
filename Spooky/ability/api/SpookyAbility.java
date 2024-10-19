package me.ShinyShadow_.Spooky.ability.api;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;

import me.ShinyShadow_.Spooky.SpookyElement;

import org.bukkit.entity.Player;

public abstract class SpookyAbility extends ElementalAbility implements AddonAbility {

    public SpookyAbility(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return (Element)SpookyElement.SPOOKY;
    }
}
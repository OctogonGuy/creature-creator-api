package tech.octopusdragon.creaturecreator.model;

import org.springframework.data.annotation.Id;
import tech.octopusdragon.creaturecreator.enums.Color;
import tech.octopusdragon.creaturecreator.enums.Shape;

public record Creature(
        @Id Long id,
        String name,
        Shape bodyShape,
        Color bodyColor,
        Shape eyeShape,
        Color eyeColor,
        Boolean antenna,
        Boolean horns,
        Boolean tail,
        Boolean ears,
        Boolean proboscis
) {}

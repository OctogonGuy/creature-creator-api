package tech.octopusdragon.creaturecreator.model;

import org.springframework.data.annotation.Id;
import tech.octopusdragon.creaturecreator.enums.Shape;

public record Creature(
        @Id Long id,
        String name,
        Shape bodyShape,
        String bodyColor,
        Shape eyeShape,
        String eyeColor,
        Boolean antenna,
        Boolean horns,
        Boolean tail,
        Boolean ears,
        Boolean proboscis
) {}

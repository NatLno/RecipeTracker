package uqac.dim.recipetracker.RecetteFile;

public class Instruction {

    int idRecette;

    int idInstruction;

    String instruction;

    public Instruction() { }

    public Instruction(int idRecette, int idInstruction, String instruction) {
        this.idRecette = idRecette;
        this.idInstruction = idInstruction;
        this.instruction = instruction;
    }

    public int getIdRecette() { return idRecette; }
    public int getIdInstruction() { return idInstruction; }
    public String getInstruction() { return instruction; }

    public void setIdRecette(int idRecette) { this.idRecette = idRecette; }
    public void setIdInstruction(int idInstruction) { this.idInstruction = idInstruction; }
    public void setInstruction(String instruction) { this.instruction = instruction; }
}

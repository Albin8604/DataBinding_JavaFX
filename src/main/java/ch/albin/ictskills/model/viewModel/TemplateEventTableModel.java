package ch.albin.ictskills.model.viewModel;

public class TemplateEventTableModel {
    private String round;
    private String activityDeckCount;
    private String electionTracker;
    private String playedB;
    private String playedR;
    /*
    @TableIgnore
    private Player leader;
    @TableIgnore
    private Player assistant;

     */
    private String voteAccepted;
    private String playedActivity;
    private String leaderDraw;
    private String assistantDraw;

    public TemplateEventTableModel() {
    }

    public TemplateEventTableModel(String round, String activityDeckCount, String electionTracker, String playedB, String playedR, String voteAccepted, String playedActivity, String leaderDraw, String assistantDraw) {
        this.round = round;
        this.activityDeckCount = activityDeckCount;
        this.electionTracker = electionTracker;
        this.playedB = playedB;
        this.playedR = playedR;
        /*
        this.leader = leader;
        this.assistant = assistant;

         */
        this.voteAccepted = voteAccepted;
        this.playedActivity = playedActivity;
        this.leaderDraw = leaderDraw;
        this.assistantDraw = assistantDraw;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }
    public void setRound(int round) {
        this.round = String.valueOf(round);
    }

    public String getActivityDeckCount() {
        return activityDeckCount;
    }

    public void setActivityDeckCount(String activityDeckCount) {
        this.activityDeckCount = activityDeckCount;
    }
    public void setActivityDeckCount(int activityDeckCount) {
        this.activityDeckCount = String.valueOf(activityDeckCount);
    }

    public String getElectionTracker() {
        return electionTracker;
    }

    public void setElectionTracker(String electionTracker) {
        this.electionTracker = electionTracker;
    }
    public void setElectionTracker(int electionTracker) {
        this.electionTracker = String.valueOf(electionTracker);
    }

    public String getPlayedB() {
        return playedB;
    }

    public void setPlayedB(String playedB) {
        this.playedB = playedB;
    }
    public void setPlayedB(int playedB) {
        this.playedB = String.valueOf(playedB);
    }

    public String getPlayedR() {
        return playedR;
    }

    public void setPlayedR(String playedR) {
        this.playedR = playedR;
    }
    public void setPlayedR(int playedR) {
        this.playedR = String.valueOf(playedR);
    }

    /*
    public Player getLeader() {
        return leader;
    }

    @JsonIgnore
    @TableCol(forCol = "leader")
    public HBox getLeaderHbox() {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: "+leader.getColorHex()+"");
        hBox.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(leader.getName());

        hBox.getChildren().add(label);
        return hBox;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public Player getAssistant() {
        return assistant;
    }

    @JsonIgnore
    @TableCol(forCol = "assistant")
    public HBox getAssistantHbox() {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: "+assistant.getColorHex()+"");
        hBox.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(assistant.getName());

        hBox.getChildren().add(label);
        return hBox;
    }



    public void setAssistant(Player assistant) {
        this.assistant = assistant;
    }


     */
    public String getVoteAccepted() {
        return voteAccepted;
    }

    public void setVoteAccepted(String voteAccepted) {
        this.voteAccepted = voteAccepted;
    }
    public void setVoteAccepted(boolean voteAccepted) {
        this.voteAccepted = voteAccepted ? "yes" : "no";
    }

    public String getPlayedActivity() {
        return playedActivity;
    }

    public void setPlayedActivity(String playedActivity) {
        this.playedActivity = playedActivity;
    }

    public String getLeaderDraw() {
        return leaderDraw;
    }

    public void setLeaderDraw(String leaderDraw) {
        this.leaderDraw = leaderDraw;
    }

    public String getAssistantDraw() {
        return assistantDraw;
    }

    public void setAssistantDraw(String assistantDraw) {
        this.assistantDraw = assistantDraw;
    }

    @Override
    public String toString() {
        return "EventTableModel{" +
                "round='" + round + '\'' +
                ", activityDeckCount='" + activityDeckCount + '\'' +
                ", electionTracker='" + electionTracker + '\'' +
                ", playedB='" + playedB + '\'' +
                ", playedR='" + playedR + '\'' +
                ", voteAccepted='" + voteAccepted + '\'' +
                ", playedActivity='" + playedActivity + '\'' +
                ", leaderDraw='" + leaderDraw + '\'' +
                ", assistantDraw='" + assistantDraw + '\'' +
                '}';
    }
}

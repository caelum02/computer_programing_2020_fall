import java.util.*;

public class Diary {

    private List<DiaryEntry> entries;
    private Map<Integer, Set<String>> searchMap;

    private static String DATA_PATH = "data/";

	public Diary() {
    	//TODO
        entries = new ArrayList<>();
        searchMap = new HashMap<>();

        loadEntries();
	}
	
    public void createEntry() {
    	//TODO
        // Practice 1 - Create a diary entry
        String title = DiaryUI.input("title: ");
        String content = DiaryUI.input("content: ");

        DiaryEntry diaryEntry = new DiaryEntry(title, content);

        entries.add(diaryEntry);
        addSearchKeywords(diaryEntry);
        saveEntry(diaryEntry);

        DiaryUI.print("The entry is saved.");

        // Practice 2 - Store the created entry in a file

    }

    public void listEntries() {
    	//TODO
        // Practice 1 - List all the entries - sorted in created time by descending order
        ListIterator<DiaryEntry> iter = entries.listIterator(entries.size());
        while(iter.hasPrevious()) {
            DiaryUI.print(iter.previous().getShortString());
        }

        // Practice 2 - Your list should contain previously stored files

    }

    public void readEntry(int id) {
    	//TODO
        // Practice 1 - Read the entry of given id

        for(DiaryEntry diaryEntry: entries) {
            if(diaryEntry.getID() == id){
                DiaryUI.print(diaryEntry.getFullString());
                return;
            }
        }

        DiaryUI.print("There is no entry with id " + id + ".");
        return;
        // Practice 2 - Your read should contain previously stored files
    }

    public void deleteEntry(int id) {
    	//TODO
        // Practice 1 - Delete the entry of given id

        DiaryEntry diaryEntry = findEntry(id);

        if(diaryEntry == null) {
            DiaryUI.print("There is no entry with id " + id);
        }

        if (!StorageManager.deleteFile(DATA_PATH + diaryEntry.getFileName())) {
            entries.remove(diaryEntry);
            searchMap.remove(id);
        }


        // Practice 2 - Delete the file of the entry
    }

    public void searchEntry(String keyword) {
        //TODO
        // Practice 1 - Search and print all the entries containing given keyword

        List<DiaryEntry> searchResult = new ArrayList<>();

        for (int id: searchMap.keySet()) {
            Set<String> keywords = searchMap.get(id);
            if(keywords.contains(keyword)) {
                searchResult.add(findEntry(id));
            }
        }

        if (searchResult.isEmpty()) {
            DiaryUI.print("There is no entry that contains \"" + keyword + "\".");
            return;
        }

        for (DiaryEntry entry: searchResult) {
            DiaryUI.print(entry.getFullString());
            DiaryUI.print("");
        }

        // Practice 2 - Your search should contain previously stored files
    }

    private DiaryEntry findEntry(int id) {
	    for(DiaryEntry entry: entries) {
	        if (entry.getID() == id)
	            return entry;
        }
	    return null;
    }

    private void addSearchKeywords(DiaryEntry entry) {
	    Set<String> keywords = new HashSet<>();

        Collections.addAll(keywords, entry.getTitle().split("\\s"));
        Collections.addAll(keywords, entry.getContent().split("\\s"));

        searchMap.put(entry.getID(), keywords);
    }

    private void saveEntry(DiaryEntry diaryEntry) {
        StorageManager.writeLines(DATA_PATH + diaryEntry.getFileName(), diaryEntry.getFileData());
    }

    private void loadEntries() {
        List<List<String>> entryDataList = StorageManager.directoryChildrenLines(DATA_PATH);
        for(List<String> entryData: entryDataList) {
            if(entryData.size() < 4) {
                continue;
            }

            DiaryEntry entry = new DiaryEntry(Integer.parseInt(entryData.get(0)), entryData.get(1), entryData.get(2), entryData.get(3));
            entries.add(entry);
            addSearchKeywords(entry);

            DiaryEntry.updateIncrementId(Integer.parseInt(entryData.get(0)));
        }
    }
}

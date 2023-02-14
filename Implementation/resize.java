//give suggestion
pu = new ArrayList<>();
for (int i = 0; i < n; i++) {
    pu.add(new ArrayList<>());
    for (int j = 0; j < n; j++) {
        pu.get(i).add(0);
    }
}
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        if (i == j) {
            pu.get(i).set(j, 0);
        } else {
            pu.get(i).set(j, Integer.MAX_VALUE);
        }
    }
}

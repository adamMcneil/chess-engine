import pandas as pd
import matplotlib.pyplot as plt

# Load JSON
df_old = pd.read_json('benchmark_results.json')
df_new = pd.read_json('benchmark_results_7933.json')

# Plot
plt.figure(figsize=(10,6))
plt.bar(2 * df_old.index, df_old['average'], label='Average Time')
plt.bar(2 * df_old.index + 0.1, df_old['min'], label='Min Time', alpha=0.7)
plt.bar(2 * df_new.index + 1, df_new['average'], label='Average Time')
plt.bar(2 * df_new.index + 1.1, df_new['min'], label='Min Time', alpha=0.7)
plt.xlabel('FEN')
plt.ylabel('Time (seconds)')
plt.title('Chess Position Search Runtimes')
plt.legend()
plt.xticks(rotation=90)
plt.tight_layout()
plt.show()

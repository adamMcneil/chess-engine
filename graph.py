import pandas as pd
import matplotlib.pyplot as plt

# Load JSON
df = pd.read_json('benchmark_results.json')
print(df)

# Plot
plt.figure(figsize=(10,6))
plt.bar(df['fen'], df['average'], label='Average Time')
plt.bar(df['fen'], df['min'], label='Min Time', alpha=0.7)
plt.xlabel('FEN')
plt.ylabel('Time (seconds)')
plt.title('Chess Position Search Runtimes')
plt.legend()
plt.xticks(rotation=90)
plt.tight_layout()
plt.show()
